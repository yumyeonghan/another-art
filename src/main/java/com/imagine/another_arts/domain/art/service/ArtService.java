package com.imagine.another_arts.domain.art.service;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.art.enums.SaleType;
import com.imagine.another_arts.domain.art.repository.ArtRepository;
import com.imagine.another_arts.domain.art.service.dto.UploadArtImageInfo;
import com.imagine.another_arts.domain.art.service.dto.request.ArtRegisterRequestDto;
import com.imagine.another_arts.domain.art.service.dto.response.ArtResponse;
import com.imagine.another_arts.domain.art.service.dto.response.AuctionArtResponse;
import com.imagine.another_arts.domain.art.service.dto.response.GeneralArtResponse;
import com.imagine.another_arts.domain.arthashtag.ArtHashtag;
import com.imagine.another_arts.domain.arthashtag.repository.ArtHashtagRepository;
import com.imagine.another_arts.domain.auction.Auction;
import com.imagine.another_arts.domain.auction.repository.AuctionRepository;
import com.imagine.another_arts.domain.auctionhistory.AuctionHistory;
import com.imagine.another_arts.domain.auctionhistory.repository.AuctionHistoryRepository;
import com.imagine.another_arts.domain.likeart.LikeArt;
import com.imagine.another_arts.domain.likeart.repository.LikeArtRepository;
import com.imagine.another_arts.domain.purchase.repository.PurchaseHistoryRepository;
import com.imagine.another_arts.domain.user.User;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.AnotherArtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.imagine.another_arts.exception.AnotherArtErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtService {
    private final UserRepository userRepository;
    private final ArtRepository artRepository;
    private final AuctionRepository auctionRepository;
    private final ArtHashtagRepository artHashtagRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final LikeArtRepository likeArtRepository;
    private final AuctionHistoryRepository auctionHistoryRepository;

    @Value("${file.dir}")
    private String fileDir;

    // 작품 등록
    @Transactional
    public Long registerArt(ArtRegisterRequestDto registerRequest) {
        try {
            User artOwner = userRepository.findById(registerRequest.getUserId())
                    .orElseThrow(() -> AnotherArtException.type(USER_NOT_FOUND));

            MultipartFile uploadFile = registerRequest.getFile();
            UploadArtImageInfo fileInfo = getUploadArtImageInfo(uploadFile);

            Art art = Art.createArt(
                    artOwner, registerRequest.getName(), registerRequest.getDescription(),
                    registerRequest.getInitPrice(), fileInfo.getUploadName(), fileInfo.getStoregeName()
            );
            art.chooseSaleType(registerRequest.getSaleType());
            artRepository.save(art);
            insertHashtagList(registerRequest.getHashtagList(), art);

            if (registerRequest.getSaleType().equals("auction")) {
                auctionRepository.save(Auction.createAuction(registerRequest.getInitPrice(), registerRequest.getStartDate(), registerRequest.getEndDate(), art));
            }

            uploadFile.transferTo(new File(fileDir + fileInfo.getStoregeName())); // 파일 저장
            return art.getId();
        } catch (IOException e) {
            throw AnotherArtException.type(RUN_TIME_ART_REGISTER);
        }
    }

    private UploadArtImageInfo getUploadArtImageInfo(MultipartFile file) {
        if (file == null) {
            throw AnotherArtException.type(ILLEGAL_ART_UPLOAD);
        }

        String uploadName = file.getOriginalFilename();
        assert uploadName != null;
        String storageName = generateServerStorageName(uploadName);

        return new UploadArtImageInfo(uploadName, storageName);
    }

    private String generateServerStorageName(@NotNull String uploadName) {
        String ext = uploadName.substring(uploadName.lastIndexOf(".") + 1);
        String name = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        return name + "." + ext;
    }

    // 작품 등록간 해시태그 등록
    private void insertHashtagList(List<String> hashtagNameList, Art saveArt) {
        if (hashtagNameList == null || hashtagNameList.size() == 0) {
            return;
        }
        hashtagNameList.forEach(name -> artHashtagRepository.save(ArtHashtag.insertArtHashtag(saveArt, name)));
    }

    // 작품 단건 조회
    public <T extends ArtResponse> T getSingleArt(Long artId) {
        Art findArt = artRepository.findArtByArtId(artId)
                .orElseThrow(() -> AnotherArtException.type(ART_NOT_FOUND));

        if (findArt.getSaleType().equals(SaleType.AUCTION)) {
            return (T) new AuctionArtResponse(
                    auctionHistoryRepository.getAuctionHistoryCountByArtId(artId),
                    artRepository.findSingleAuctionArtByArtId(findArt.getId()),
                    artHashtagRepository.findHashtagListByArtId(artId)
            );
        } else {
            return (T) new GeneralArtResponse(
                    likeArtRepository.getLikeArtCountByArtId(artId),
                    artRepository.findSingleGeneralArtByArtId(findArt.getId()),
                    artHashtagRepository.findHashtagListByArtId(artId)
            );
        }
    }

    // 작품 정보 변경
    @Transactional
    public void editArt(Long artId, String updateName, String updateDescription) {
        Art findArt = artRepository.findById(artId)
                .orElseThrow(() -> AnotherArtException.type(ART_NOT_FOUND));

        if (StringUtils.hasText(updateName)) {
            hasDuplicateArtNameInModification(artId, updateName);
            findArt.changeArtName(updateName);
        }

        if (StringUtils.hasText(updateDescription)) {
            findArt.changeDescription(updateDescription);
        }
    }

    private void hasDuplicateArtNameInModification(Long artId, String updateName) {
        if (artRepository.existsByIdNotAndName(artId, updateName)) {
            throw AnotherArtException.type(DUPLICATE_ART_NAME);
        }
    }

    // 작품 삭제
    @Transactional
    public void deleteArt(Long artId) {
        Art findArt = artRepository.findById(artId)
                .orElseThrow(() -> AnotherArtException.type(ART_NOT_FOUND));

        hasPurchaseHistory(findArt);
        if (findArt.getSaleType().equals(SaleType.AUCTION)) {
            hasAtLeastOneBid(findArt);

            Auction auction = auctionRepository.findAuctionByArt(findArt);
            auctionRepository.delete(auction);
        }

        artHashtagRepository.deleteInBatchByArtId(artId);
        artRepository.delete(findArt);
    }

    private void hasPurchaseHistory(Art art) {
        if (purchaseHistoryRepository.existsByArt(art)) {
            throw AnotherArtException.type(ART_SOLD_OUT);
        }
    }

    private void hasAtLeastOneBid(Art art) {
        if (auctionHistoryRepository.existsByArtAndUserIsNotNull(art)) {
            throw AnotherArtException.type(ALREADY_AUCTION_PROCESS);
        }
    }

    // 해시태그 추가
    @Transactional
    public void addHashtag(Long artId, List<String> hashtagNameList) {
        if (hashtagNameList == null || hashtagNameList.size() == 0) {
            return;
        }

        Art findArt = artRepository.findById(artId)
                .orElseThrow(() -> AnotherArtException.type(ART_NOT_FOUND));
        List<String> hashtagList = artHashtagRepository.findHashtagListByArtId(artId);

        hashtagNameList.forEach(name -> {
            if(isNotContainsNewHashtag(hashtagList, name)) {
                artHashtagRepository.save(ArtHashtag.insertArtHashtag(findArt, name));
            }
        });
    }

    private boolean isNotContainsNewHashtag(List<String> hashtagList, String hashtagName) {
        return !hashtagList.contains(hashtagName);
    }

    // 해시태그 삭제
    @Transactional
    public void deleteHashtag(Long artId, List<String> hashtagList) {
        if (hashtagList == null || hashtagList.size() == 0) {
            return;
        }
        artHashtagRepository.deleteInBatchByArtIdAndHashtagIn(artId, hashtagList);
    }

    // 메인페이지 정렬 기준에 따른 "경매 작품"
    public List<AuctionArtResponse> getSortedAuctionArtList(String sortType, Pageable pageRequest) {
        List<AuctionHistory> auctionHistoryList = auctionHistoryRepository.findAuctionHistoryList();
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtagList();

        return artRepository.findAuctionArt(sortType, pageRequest)
                .stream()
                .map(basicAuctionArtResponse -> new AuctionArtResponse(
                        getAuctionBidCountByArtId(auctionHistoryList, basicAuctionArtResponse.getArtId()),
                        basicAuctionArtResponse,
                        getHashtagListByArtId(artHashtagList, basicAuctionArtResponse.getArtId())
                )).collect(Collectors.toList());
    }

    // 해시태그 기반 "경매 작품" 검색
    public List<AuctionArtResponse> getAuctionArtListSearchByHashtag(String hashtag, String sortType, Pageable pageRequest) {
        List<AuctionHistory> auctionHistoryList = auctionHistoryRepository.findAuctionHistoryList();
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtagList();

        return artRepository.findAuctionArtHashtagSearch(hashtag, sortType, pageRequest)
                .stream()
                .map(basicAuctionArtResponse -> new AuctionArtResponse(
                        getAuctionBidCountByArtId(auctionHistoryList, basicAuctionArtResponse.getArtId()),
                        basicAuctionArtResponse,
                        getHashtagListByArtId(artHashtagList, basicAuctionArtResponse.getArtId())
                )).collect(Collectors.toList());
    }

    // 해시태그 기반 "일반 작품" 검색
    public List<GeneralArtResponse> getGeneralArtListSearchByHashtag(String hashtag, String sortType, Pageable pageRequest) {
        List<LikeArt> likeArtList = likeArtRepository.findLikeArtList();
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtagList();

        return artRepository.findGeneralArtHashtagSearch(hashtag, sortType, pageRequest)
                .stream()
                .map(basicGeneralArtResponse -> new GeneralArtResponse(
                        getLikeArtCountByArtId(likeArtList, basicGeneralArtResponse.getArtId()),
                        basicGeneralArtResponse,
                        getHashtagListByArtId(artHashtagList, basicGeneralArtResponse.getArtId())
                )).collect(Collectors.toList());
    }

    // 키워드 기반 "경매 작품" 검색
    public List<AuctionArtResponse> getAuctionArtListSearchByKeyword(String keyword, String sortType, Pageable pageRequest) {
        List<AuctionHistory> auctionHistoryList = auctionHistoryRepository.findAuctionHistoryList();
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtagList();

        return artRepository.findAuctionArtKeywordSearch(keyword, sortType, pageRequest)
                .stream()
                .map(basicAuctionArtResponse -> new AuctionArtResponse(
                        getAuctionBidCountByArtId(auctionHistoryList, basicAuctionArtResponse.getArtId()),
                        basicAuctionArtResponse,
                        getHashtagListByArtId(artHashtagList, basicAuctionArtResponse.getArtId())
                )).collect(Collectors.toList());
    }

    // 키워드 기반 "일반 작품" 검색
    public List<GeneralArtResponse> getGeneralArtListSearchByKeyword(String keyword, String sortType, Pageable pageRequest) {
        List<LikeArt> likeArtList = likeArtRepository.findLikeArtList();
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtagList();

        return artRepository.findGeneralArtKeywordSearch(keyword, sortType, pageRequest)
                .stream()
                .map(basicGeneralArtResponse -> new GeneralArtResponse(
                        getLikeArtCountByArtId(likeArtList, basicGeneralArtResponse.getArtId()),
                        basicGeneralArtResponse,
                        getHashtagListByArtId(artHashtagList, basicGeneralArtResponse.getArtId())
                )).collect(Collectors.toList());
    }

    // 작품 좋아요
    @Transactional
    public void likeArt(Long artId, Long userId) {
        Art findArt = artRepository.findArtByArtId(artId)
                .orElseThrow(() -> AnotherArtException.type(ART_NOT_FOUND));
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> AnotherArtException.type(USER_NOT_FOUND));

        isAlreadyLikeMarked(findArt, findUser);
        isSelfLikeMarked(findArt, findUser);
        likeArtRepository.save(LikeArt.insertLikeArt(findArt, findUser));
    }

    // 작품 좋아요 취소
    @Transactional
    public void cancelArt(Long artId, Long userId) {
        Art findArt = artRepository.findById(artId)
                .orElseThrow(() -> AnotherArtException.type(ART_NOT_FOUND));
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> AnotherArtException.type(USER_NOT_FOUND));

        isAlreadyCancel(findArt, findUser);
        likeArtRepository.deleteByArtAndUser(findArt.getId(), findUser.getId());
    }

    private void isAlreadyLikeMarked(Art art, User user) {
        if (likeArtRepository.existsByArtAndUser(art, user)) {
            throw AnotherArtException.type(ALREADY_LIKE_MARKING);
        }
    }

    private void isSelfLikeMarked(Art art, User user) {
        if (art.getUser().getId().equals(user.getId())) {
            throw AnotherArtException.type(ILLEGAL_LIKE_MARKING);
        }
    }

    private void isAlreadyCancel(Art art, User user) {
        if (!likeArtRepository.existsByArtAndUser(art, user)) {
            throw AnotherArtException.type(ILLEGAL_LIKE_MARKING_CANCEL);
        }
    }

    // [art_id]에 대한 경매 비드 횟수
    private Long getAuctionBidCountByArtId(List<AuctionHistory> auctionHistoryList, Long artId) {
        return auctionHistoryList.stream()
                .filter(auctionHistory -> auctionHistory.getArt().getId().equals(artId))
                .count();
    }

    // [art_id]에 대한 좋아요 횟수
    private Long getLikeArtCountByArtId(List<LikeArt> likeArtList, Long artId) {
        return likeArtList.stream()
                .filter(likeArt -> likeArt.getArt().getId().equals(artId))
                .count();
    }

    // [art_id]의 해시태그 리스트
    private List<String> getHashtagListByArtId(List<ArtHashtag> artHashtagList, Long artId) {
        return artHashtagList.stream()
                .filter(artHashtag -> artHashtag.getArt().getId().equals(artId))
                .map(ArtHashtag::getName)
                .collect(Collectors.toList());
    }

    public void checkDuplicateArtName(String artName) {
        if (artRepository.existsByName(artName)) {
            throw AnotherArtException.type(DUPLICATE_ART_NAME);
        }
    }
}