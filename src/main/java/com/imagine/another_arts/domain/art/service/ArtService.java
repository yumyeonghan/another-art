package com.imagine.another_arts.domain.art.service;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.art.enums.SaleType;
import com.imagine.another_arts.domain.art.repository.ArtRepository;
import com.imagine.another_arts.domain.art.service.dto.UploadArtImageInfo;
import com.imagine.another_arts.domain.art.service.dto.request.ArtEditRequestDto;
import com.imagine.another_arts.domain.art.service.dto.request.ArtRegisterRequestDto;
import com.imagine.another_arts.domain.art.service.dto.response.AuctionArtResponse;
import com.imagine.another_arts.domain.art.service.dto.response.BasicAuctionArtResponse;
import com.imagine.another_arts.domain.art.service.dto.response.BasicGeneralArtResponse;
import com.imagine.another_arts.domain.art.service.dto.response.GeneralArtResponse;
import com.imagine.another_arts.domain.arthashtag.ArtHashtag;
import com.imagine.another_arts.domain.arthashtag.repository.ArtHashtagRepository;
import com.imagine.another_arts.domain.auction.Auction;
import com.imagine.another_arts.domain.auction.repository.AuctionRepository;
import com.imagine.another_arts.domain.auctionhistory.AuctionHistory;
import com.imagine.another_arts.domain.auctionhistory.repository.AuctionHistoryRepository;
import com.imagine.another_arts.domain.hashtag.Hashtag;
import com.imagine.another_arts.domain.hashtag.repository.HashtagRepository;
import com.imagine.another_arts.domain.likeart.LikeArt;
import com.imagine.another_arts.domain.likeart.repository.LikeArtRepository;
import com.imagine.another_arts.domain.purchase.repository.PurchaseHistoryRepository;
import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class ArtService {
    private final UserRepository userRepository;
    private final ArtRepository artRepository;
    private final AuctionRepository auctionRepository;
    private final ArtHashtagRepository artHashtagRepository;
    private final HashtagRepository hashtagRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final LikeArtRepository likeArtRepository;
    private final AuctionHistoryRepository auctionHistoryRepository;

    @Value("${file.dir}")
    private String fileDir;

    // 작품 등록
    @Transactional
    public Long registerArt(ArtRegisterRequestDto artRegisterRequestDto) {
        try {
            Users findArtOwner = userRepository.findById(artRegisterRequestDto.getUserId())
                    .orElseThrow(() -> new UnAuthenticatedUserException("가입하지 않은 사용자에 대한 작품 등록 권한은 없습니다"));

            MultipartFile uploadFile = artRegisterRequestDto.getFile();
            UploadArtImageInfo fileInfo = getUploadArtImageInfo(uploadFile);

            Art registerArt = Art.createArt(
                    findArtOwner,
                    artRegisterRequestDto.getName(),
                    artRegisterRequestDto.getDescription(),
                    artRegisterRequestDto.getInitPrice(),
                    fileInfo.getUploadName(),
                    fileInfo.getStoregeName()
            );
            registerArt.chooseSaleType(artRegisterRequestDto.getSaleType()); // 작품 타입 지정

            validateArtNameUnique(registerArt.getName()); // 작품명 2차 검증
            artRepository.save(registerArt);
            insertHashtagList(artRegisterRequestDto.getHashtagList(), registerArt);

            if (artRegisterRequestDto.getSaleType().equals("auction")) {
                Auction registerAuction = Auction.createAuction(
                        artRegisterRequestDto.getInitPrice(),
                        artRegisterRequestDto.getStartDate(),
                        artRegisterRequestDto.getEndDate(),
                        registerArt
                );
                auctionRepository.save(registerAuction);
            }

            uploadFile.transferTo(new File(fileDir + fileInfo.getStoregeName())); // 파일 저장
            return registerArt.getId();
        } catch (IOException e) {
            throw new RunTimeArtRegisterException("작품 등록 과정에서 서버상에 오류가 발생했습니다");
        }
    }

    private void validateArtNameUnique(String artName) {
        if (artRepository.existsByName(artName)) {
            throw new DuplicationArtNameException("중복된 작품명입니다.");
        }
    }

    private UploadArtImageInfo getUploadArtImageInfo(MultipartFile file) {
        if (file == null) {
            throw new IllegalArtFileUploadException("파일이 업로드되지 않았습니다");
        }

        String uploadName = file.getOriginalFilename();
        assert uploadName != null;
        String storageName = generateServerStorageName(uploadName);

        return new UploadArtImageInfo(uploadName, storageName);
    }

    private String generateServerStorageName(String uploadName) {
        String ext = uploadName.substring(uploadName.lastIndexOf(".") + 1);
        String name = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        return name + "." + ext;
    }

    // 작품 등록간 해시태그 등록
    @Transactional
    protected void insertHashtagList(List<String> hashtagNameList, Art saveArt) {
        if (hashtagNameList == null) {
            return;
        }

        List<Hashtag> hashtagList = hashtagRepository.findAll();
        hashtagNameList.forEach(name -> {
            Hashtag hashtag = getOrCreateHashtag(hashtagList, name);
            artHashtagRepository.save(ArtHashtag.insertArtHashtag(saveArt, hashtag));
        });
    }

    @Transactional
    public Hashtag getOrCreateHashtag(List<Hashtag> hashtagList, String name) {
        return hashtagList.stream()
                .filter(hashtag -> hashtag.getName().equals(name))
                .findFirst()
                .orElseGet(() -> {
                    Hashtag hashtag = Hashtag.createHashtag(name);
                    hashtagRepository.save(hashtag);
                    return hashtag;
                });
    }

    // 작품 단건 조회
    public <T> T getSingleArt(Long artId) {
        Art findArt = artRepository.findFirstArtBy(artId)
                .orElseThrow(() -> new ArtNotFoundException("작품 정보가 존재하지 않습니다"));

        if (findArt.getSaleType().equals(SaleType.AUCTION)) {
            List<AuctionHistory> auctionHistoryList = auctionHistoryRepository.findAuctionHistoryBy();
            List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtagBy();
            Auction findAuctionByArtId = auctionRepository.findFirstAuctionBy(artId);

            return (T) new AuctionArtResponse(
                    getAuctionBidCountByArtId(auctionHistoryList, artId),
                    new BasicAuctionArtResponse(findAuctionByArtId, findArt),
                    getHashtagListByArtId(artHashtagList, artId)
            );
        } else {
            List<LikeArt> likeArtList = likeArtRepository.findLikeArtBy();
            List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtagBy();

            return (T) new GeneralArtResponse(
                    getLikeArtCountByArtId(likeArtList, artId),
                    new BasicGeneralArtResponse(findArt),
                    getHashtagListByArtId(artHashtagList, artId)
            );
        }
    }

    // 작품 정보 변경
    @Transactional
    public void editArt(Long artId, ArtEditRequestDto artEditRequestDto) {
        Art findArt = artRepository.findById(artId)
                .orElseThrow(() -> new ArtNotFoundException("작품 정보가 존재하지 않습니다"));

        if (StringUtils.hasText(artEditRequestDto.getUpdateName())) {
            validateDuplicateArtName(artId, artEditRequestDto.getUpdateName());
            findArt.changeArtName(artEditRequestDto.getUpdateName());
        }

        if (StringUtils.hasText(artEditRequestDto.getUpdateDescription())) {
            findArt.changeDescription(artEditRequestDto.getUpdateDescription());
        }
    }

    private void validateDuplicateArtName(Long artId, String updateName) {
        if (artRepository.existsByIdNotAndName(artId, updateName)) {
            throw new IllegalArtModifyException("변경하려는 작품 이름이 이미 존재합니다");
        }
    }

    // 작품 삭제
    @Transactional
    public void deleteArt(Long artId) {
        Art findArt = artRepository.findById(artId)
                .orElseThrow(() -> new ArtNotFoundException("작품 정보가 존재하지 않습니다"));

        validateAlreadyPurchase(findArt);
        if (findArt.getSaleType().equals(SaleType.AUCTION)) {
            validateAuctionBidProcess(findArt);

            Auction auction = auctionRepository.findByArt(findArt);
            auctionRepository.delete(auction);
        }

        List<ArtHashtag> artHashtagList = artHashtagRepository.findByArtId(artId);
        artHashtagRepository.deleteAll(artHashtagList);
        artRepository.delete(findArt);
    }

    private void validateAlreadyPurchase(Art art) {
        if (purchaseHistoryRepository.existsByArt(art)) {
            throw new IllegalArtDeleteException("이미 거래된 작품은 삭제할 수 없습니다");
        }
    }

    private void validateAuctionBidProcess(Art art) {
        if (auctionHistoryRepository.existsByArtAndUserIsNotNull(art)) {
            throw new IllegalArtDeleteException("입찰이 한번이라도 진행된 경매 작품은 삭제할 수 없습니다");
        }
    }

    // 해시태그 추가
    @Transactional
    public void addHashtag(Long artId, List<String> hashtagNameList) {
        if (hashtagNameList == null) {
            return;
        }

        Art findArt = artRepository.findById(artId)
                .orElseThrow(() -> new ArtNotFoundException("작품 정보가 존재하지 않습니다"));
        List<ArtHashtag> artHashtagByArtId = artHashtagRepository.findArtHashtagByArtId(artId);
        List<Hashtag> hashtagList = hashtagRepository.findAll();
        hashtagNameList.forEach(name -> {
            Hashtag hashtag = getOrCreateHashtag(hashtagList, name);

            // findArt에 대해서 동일한 Hashtag가 이미 있는지에 대한 Validation
            if (!validateDuplicateHashtagName(artHashtagByArtId, findArt, hashtag)) {
                artHashtagRepository.save(ArtHashtag.insertArtHashtag(findArt, hashtag));
            }
        });
    }

    private boolean validateDuplicateHashtagName(List<ArtHashtag> artHashtagList, Art art, Hashtag hashtag) {
        return artHashtagList.stream()
                .filter(artHashtag -> artHashtag.getArt().equals(art))
                .anyMatch(artHashtag -> artHashtag.getHashtag().equals(hashtag));
    }

    // 해시태그 삭제
    @Transactional
    public void deleteHashtag(Long artId, String name) {
        ArtHashtag artHashtag = validateHashtagExists(artId, name);
        artHashtagRepository.delete(artHashtag);
    }

    private ArtHashtag validateHashtagExists(Long artId, String hashtagName) {
        Optional<ArtHashtag> findArtHashtag = artHashtagRepository.findByArtIdAndHashtagName(artId, hashtagName);

        if (findArtHashtag.isEmpty()) {
            throw new IllegalHashtagDeleteException("해시태그가 이미 삭제되거나 없습니다");
        }
        return findArtHashtag.get();
    }

    // 메인페이지 정렬 기준에 따른 "경매 작품"
    public List<AuctionArtResponse> getSortedAuctionArtList(String sortType, Pageable pageRequest) {
        List<AuctionHistory> auctionHistoryList = auctionHistoryRepository.findAuctionHistoryBy();
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtagBy();

        return artRepository.findAuctionArt(sortType, pageRequest)
                .stream()
                .map(basicAuctionArtResponse -> new AuctionArtResponse(
                        getAuctionBidCountByArtId(auctionHistoryList, basicAuctionArtResponse.getArtId().longValue()),
                        basicAuctionArtResponse,
                        getHashtagListByArtId(artHashtagList, basicAuctionArtResponse.getArtId().longValue())
                )).toList();
    }

    // 해시태그 기반 "경매 작품" 검색
    public List<AuctionArtResponse> getAuctionArtListSearchByHashtag(String hashtag, String sortType, Pageable pageRequest) {
        List<AuctionHistory> auctionHistoryList = auctionHistoryRepository.findAuctionHistoryBy();
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtagBy();

        return artRepository.findAuctionArtHashtagSearch(hashtag, sortType, pageRequest)
                .stream()
                .map(basicAuctionArtResponse -> new AuctionArtResponse(
                        getAuctionBidCountByArtId(auctionHistoryList, basicAuctionArtResponse.getArtId().longValue()),
                        basicAuctionArtResponse,
                        getHashtagListByArtId(artHashtagList, basicAuctionArtResponse.getArtId().longValue())
                )).toList();
    }

    // 해시태그 기반 "일반 작품" 검색
    public List<GeneralArtResponse> getGeneralArtListSearchByHashtag(String hashtag, String sortType, Pageable pageRequest) {
        List<LikeArt> likeArtList = likeArtRepository.findLikeArtBy();
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtagBy();

        return artRepository.findGeneralArtHashtagSearch(hashtag, sortType, pageRequest)
                .stream()
                .map(basicGeneralArtResponse -> new GeneralArtResponse(
                        getLikeArtCountByArtId(likeArtList, basicGeneralArtResponse.getArtId().longValue()),
                        basicGeneralArtResponse,
                        getHashtagListByArtId(artHashtagList, basicGeneralArtResponse.getArtId().longValue())
                )).toList();
    }

    // 키워드 기반 "경매 작품" 검색
    public List<AuctionArtResponse> getAuctionArtListSearchByKeyword(String keyword, String sortType, Pageable pageRequest) {
        List<AuctionHistory> auctionHistoryList = auctionHistoryRepository.findAuctionHistoryBy();
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtagBy();

        return artRepository.findAuctionArtKeywordSearch(keyword, sortType, pageRequest)
                .stream()
                .map(basicAuctionArtResponse -> new AuctionArtResponse(
                        getAuctionBidCountByArtId(auctionHistoryList, basicAuctionArtResponse.getArtId().longValue()),
                        basicAuctionArtResponse,
                        getHashtagListByArtId(artHashtagList, basicAuctionArtResponse.getArtId().longValue())
                )).toList();
    }

    // 키워드 기반 "일반 작품" 검색
    public List<GeneralArtResponse> getGeneralArtListSearchByKeyword(String keyword, String sortType, Pageable pageRequest) {
        List<LikeArt> likeArtList = likeArtRepository.findLikeArtBy();
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtagBy();

        return artRepository.findGeneralArtKeywordSearch(keyword, sortType, pageRequest)
                .stream()
                .map(basicGeneralArtResponse -> new GeneralArtResponse(
                        getLikeArtCountByArtId(likeArtList, basicGeneralArtResponse.getArtId().longValue()),
                        basicGeneralArtResponse,
                        getHashtagListByArtId(artHashtagList, basicGeneralArtResponse.getArtId().longValue())
                )).toList();
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
                .map(artHashtag -> artHashtag.getHashtag().getName())
                .toList();
    }
}