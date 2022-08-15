package com.imagine.another_arts.domain.art.service;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.art.enums.SaleType;
import com.imagine.another_arts.domain.art.repository.ArtRepository;
import com.imagine.another_arts.domain.art.service.dto.ArtFileUploadDto;
import com.imagine.another_arts.domain.art.service.dto.AuctionArtResponse;
import com.imagine.another_arts.domain.art.service.dto.GeneralArtResponse;
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
import com.imagine.another_arts.domain.purchase.PurchaseHistory;
import com.imagine.another_arts.domain.purchase.repository.PurchaseHistoryRepository;
import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.*;
import com.imagine.another_arts.web.art.dto.ArtEditRequest;
import com.imagine.another_arts.web.art.dto.AuctionArtRegisterRequest;
import com.imagine.another_arts.web.art.dto.GeneralArtRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

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

    // 경매 작품 등록
    @Transactional
    public void registerAuctionArt(AuctionArtRegisterRequest auctionArtRegisterRequest) {
        try {
            Users findArtOwner = userRepository.findById(auctionArtRegisterRequest.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다"));
            MultipartFile file = auctionArtRegisterRequest.getFile();
            ArtFileUploadDto fileInfo = getMultipartFileInfo(file);

            Art saveArt = Art.createArt(
                    findArtOwner,
                    auctionArtRegisterRequest.getName(),
                    auctionArtRegisterRequest.getDescription(),
                    auctionArtRegisterRequest.getInitPrice(),
                    SaleType.AUCTION,
                    fileInfo.getUploadName(),
                    fileInfo.getStoregeName()
            );
            artRepository.save(saveArt);

            List<String> hashtagList = auctionArtRegisterRequest.getHashtagList(); // 해시태그 리스트
            insertHashtagList(hashtagList, saveArt); // [ArtHashtag, Hashtag] 테이블에 값 넣어주기

            Auction saveAuction = Auction.createAuction(
                    auctionArtRegisterRequest.getInitPrice(), // 처음에는 InitPrice로 INSERT
                    auctionArtRegisterRequest.getStartDate(),
                    auctionArtRegisterRequest.getEndDate(),
                    saveArt
            );
            auctionRepository.save(saveAuction);

            file.transferTo(new File(fileDir + fileInfo.getStoregeName())); // 파일 저장
        } catch (IOException e) {
            throw new RunTimeArtRegisterException("작품 등록 과정에서 서버상에 오류가 발생했습니다");
        }
    }

    // 일반 작품 등록
    @Transactional
    public void registerGeneralArt(GeneralArtRegisterRequest generalArtRegisterRequest) {
        try {
            Users findArtOwner = userRepository.findById(generalArtRegisterRequest.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다"));
            MultipartFile file = generalArtRegisterRequest.getFile();
            ArtFileUploadDto fileInfo = getMultipartFileInfo(file);

            Art saveArt = Art.createArt(
                    findArtOwner,
                    generalArtRegisterRequest.getName(),
                    generalArtRegisterRequest.getDescription(),
                    generalArtRegisterRequest.getInitPrice(),
                    SaleType.GENERAL,
                    fileInfo.getUploadName(),
                    fileInfo.getStoregeName()
            );
            artRepository.save(saveArt);

            List<String> hashtagList = generalArtRegisterRequest.getHashtagList(); // 해시태그 리스트
            insertHashtagList(hashtagList, saveArt); // [ArtHashtag, Hashtag] 테이블에 값 넣어주기

            file.transferTo(new File(fileDir + fileInfo.getStoregeName())); // 파일 저장
        } catch (IOException e) {
            throw new RunTimeArtRegisterException("작품 등록 과정에서 서버상에 오류가 발생했습니다");
        }
    }

    private ArtFileUploadDto getMultipartFileInfo(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArtFileUploadException("파일이 업로드되지 않았습니다");
        }

        String uploadName = file.getOriginalFilename();
        assert uploadName != null;
        String storageName = generateServerStorageName(uploadName);

        return new ArtFileUploadDto(uploadName, storageName);
    }

    private String generateServerStorageName(String uploadName){
        String ext = uploadName.substring(uploadName.lastIndexOf(".") + 1);
        String name = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        return name + "." + ext;
    }

    // 작품 등록간 해시태그 등록
    @Transactional
    protected void insertHashtagList(List<String> hashtagList, Art saveArt) {
        for (String name : hashtagList) {
            Hashtag hashtag;
            Optional<Hashtag> findHashtag = hashtagRepository.findFirstByName(name);
            if (findHashtag.isPresent()) { // 이미 해시태그가 DB상에 존재할 경우 기존 해시태그 가져오기
                hashtag = findHashtag.get();
            } else { // 해시태그가 DB상에 없을 경우 새로 INSERT
                hashtag = Hashtag.createHashtag(name);
                hashtagRepository.save(hashtag);
            }

            artHashtagRepository.save(ArtHashtag.insertArtHashtag(saveArt, hashtag));
        }
    }

    // 작품 단건 조회
    public <T> T getSpecificArt(Long artId) {
        Art findArt = artRepository.findFirstArtBy(artId)
                .orElseThrow(() -> new ArtNotFoundException("작품 정보가 존재하지 않습니다"));
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtagBy();

        if (findArt.getSaleType().equals(SaleType.AUCTION)) {
            Auction findAuction = auctionRepository.findFirstAuctionBy(artId)
                    .orElseThrow(() -> new AuctionNotFoundException("경매에 등록되지 않은 작품 입니다"));
            List<AuctionHistory> auctionHistoryList = auctionHistoryRepository.findAuctionHistoriesBy();

            return (T) new AuctionArtResponse(
                    findAuction,
                    findArt,
                    getHashtagListFromArt(artHashtagList, findArt.getId()),
                    getAuctionBidCountByArtId(auctionHistoryList, findArt.getId())
            );
        } else {
            List<LikeArt> likeArtList = likeArtRepository.findLikeArtsBy();

            return (T) new GeneralArtResponse(
                    findArt,
                    getHashtagListFromArt(artHashtagList, findArt.getId()),
                    getLikeArtCountByArtId(likeArtList, findArt.getId())
            );
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

    // 작품 정보 변경
    @Transactional
    public void editArt(Long artId, ArtEditRequest artEditRequest) {
        Art findArt = artRepository.findById(artId)
                .orElseThrow(() -> new ArtNotFoundException("작품 정보가 존재하지 않습니다"));

        if (StringUtils.hasText(artEditRequest.getName())) {
            Optional<Art> findArtByName = artRepository.findFirstByIdNotAndName(findArt.getId(), artEditRequest.getName());

            if (findArtByName.isEmpty()) {
                findArt.changeArtName(artEditRequest.getName());
            } else {
                throw new IllegalArtModifyException("변경하려는 작품 이름이 이미 존재합니다");
            }
        }

        if (StringUtils.hasText(artEditRequest.getDescription())) {
            findArt.changeDescription(artEditRequest.getDescription());
        }
    }

    // 작품 삭제
    @Transactional
    public void deleteArt(Long artId) {
        Art findArt = artRepository.findById(artId)
                .orElseThrow(() -> new ArtNotFoundException("작품 정보가 존재하지 않습니다"));

        if (findArt.getSaleType().equals(SaleType.AUCTION)) {
            throw new IllegalArtModifyException("경매로 등록된 작품은 삭제할 수 없습니다");
        } else {
            List<ArtHashtag> artHashtagList = artHashtagRepository.findByArtId(artId);
            Optional<PurchaseHistory> findPurchaseHistory = purchaseHistoryRepository.findFirstByArtId(artId);
            if (findPurchaseHistory.isPresent()) {
                throw new IllegalArtModifyException("이미 거래된 작품은 삭제할 수 없습니다");
            }

            artHashtagRepository.deleteAll(artHashtagList);
            artRepository.delete(findArt);
        }
    }

    // 해시태그 추가
    @Transactional
    public void addHashtag(Long artId, List<String> hashtagList) {
        Art findArt = artRepository.findById(artId)
                .orElseThrow(() -> new ArtNotFoundException("작품 정보가 존재하지 않습니다"));

        for (String name : hashtagList) {
            Hashtag hashtag;
            Optional<Hashtag> findHashtag = hashtagRepository.findFirstByName(name);
            if (findHashtag.isPresent()) { // 이미 해시태그가 DB상에 존재할 경우 기존 해시태그 가져오기
                hashtag = findHashtag.get();
            } else { // 해시태그가 DB상에 없을 경우 새로 INSERT
                hashtag = Hashtag.createHashtag(name);
                hashtagRepository.save(hashtag);
            }

            Optional<ArtHashtag> artHashtagByArtId = artHashtagRepository.findByArtIdAndHashtagName(artId, name);
            if (artHashtagByArtId.isEmpty()) { // 비어있으면 동일 해시태그 이름이 존재하지 않는다는 의미 (동일 해시태그 중복 저장 X)
                artHashtagRepository.save(ArtHashtag.insertArtHashtag(findArt, hashtag));
            }
        }
    }

    // 해시태그 삭제
    @Transactional
    public void deleteHashtag(Long artId, String name) {
        Art findArt = artRepository.findById(artId)
                .orElseThrow(() -> new ArtNotFoundException("작품 정보가 존재하지 않습니다"));

        artHashtagRepository.delete(artHashtagRepository.findByArtIdAndHashtagName(findArt.getId(), name)
                .orElseThrow(() -> new IllegalHashtagDeleteException("해시태그가 이미 존재하지 않습니다")));
    }

    // 메인페이지 정렬 기준에 따른 "경매 작품" 정렬
    public List<AuctionArtResponse> getArtListTypeAuction(String sortType, Pageable pageRequest){
        List<Art> auctionArtList = artRepository.findBySaleTypeAuction();
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtagBy();
        List<AuctionHistory> auctionHistoryList = auctionHistoryRepository.findAuctionHistoriesBy();

        switch (sortType) {
            case "date":  // RegisterDateDESC (RD = default)
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionOrderByRegisterDateDesc(pageRequest).getContent(),
                        artHashtagList,
                        auctionHistoryList
                );
            case "rdate":  // RegisterDateASC
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionOrderByRegisterDateAsc(pageRequest).getContent(),
                        artHashtagList,
                        auctionHistoryList
                );
            case "price":  // BidPriceDESC (BP)
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionOrderByBidPriceDesc(pageRequest).getContent(),
                        artHashtagList,
                        auctionHistoryList
                );
            case "rprice":  // BidPriceASC
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionOrderByBidPriceAsc(pageRequest).getContent(),
                        artHashtagList,
                        auctionHistoryList
                );
            case "count":  // BidCountDESC (BC)
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionOrderByAuctionHistoryCountDesc(pageRequest).getContent(),
                        artHashtagList,
                        auctionHistoryList
                );
            default: // BidCountASC
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionOrderByAuctionHistoryCountAsc(pageRequest).getContent(),
                        artHashtagList,
                        auctionHistoryList
                );
        }
    }

    private List<AuctionArtResponse> getSortedArtResultTypeAuction(List<Art> artList, List<Auction> auctionList, List<ArtHashtag> artHashtagList, List<AuctionHistory> auctionHistoryList){
        List<AuctionArtResponse> result = new ArrayList<>();
        for (Auction auction : auctionList) {
            Art auctionArt = getArtByArtId(artList, auction.getArt().getId());
            List<String> hashtagList = getHashtagListFromArt(artHashtagList, auctionArt.getId());
            Long auctionBidCountByArtId = getAuctionBidCountByArtId(auctionHistoryList, auctionArt.getId());
            result.add(new AuctionArtResponse(
                    auction,
                    auctionArt,
                    hashtagList,
                    auctionBidCountByArtId
            ));
        }
        return result;
    }

    private Art getArtByArtId(List<Art> artList, Long artId){
        return artList.stream()
                .filter(art -> Objects.equals(art.getId(), artId))
                .findAny()
                .orElseThrow(() -> new ArtNotFoundException("작품을 찾지 못했습니다"));
    }

    private List<String> getHashtagListFromArt(List<ArtHashtag> artHashtagList, Long artId) {
        List<String> result = new ArrayList<>();
        for (ArtHashtag artHashtag : artHashtagList) {
            if (Objects.equals(artHashtag.getArt().getId(), artId)) {
                result.add(artHashtag.getHashtag().getName());
            }
        }
        return result;
    }

    // 해시태그 기반 "경매 작품" 검색
    public List<AuctionArtResponse> getArtListTypeAuctionSearchedByHashtag(String hashtag, String sortType, Pageable pageRequest){
        List<Art> auctionArtList = artRepository.findBySaleTypeAuction();
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtagBy();
        List<AuctionHistory> auctionHistoryList = auctionHistoryRepository.findAuctionHistoriesBy();

        switch (sortType) {
            case "date":  // RegisterDateDESC (RD = default)
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionAndHashtagOrderByRegisterDateDesc(hashtag, pageRequest).getContent(),
                        artHashtagList,
                        auctionHistoryList
                );
            case "rdate":  // RegisterDateASC
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionAndHashtagOrderByRegisterDateAsc(hashtag, pageRequest).getContent(),
                        artHashtagList,
                        auctionHistoryList
                );
            case "price":  // BidPriceDESC (BP)
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionAndHashtagOrderByBidPriceDesc(hashtag, pageRequest).getContent(),
                        artHashtagList,
                        auctionHistoryList
                );
            case "rprice":  // BidPriceASC
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionAndHashtagOrderByBidPriceAsc(hashtag, pageRequest).getContent(),
                        artHashtagList,
                        auctionHistoryList
                );
            case "count":  // BidCountDESC (BC)
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionAndHashtagOrderByAuctionHistoryCountDesc(hashtag, pageRequest).getContent(),
                        artHashtagList,
                        auctionHistoryList
                );
            default: // BidCountASC
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionAndHashtagOrderByAuctionHistoryCountAsc(hashtag, pageRequest).getContent(),
                        artHashtagList,
                        auctionHistoryList
                );
        }
    }

    // 해시태그 기반 "일반 작품" 검색
    public List<GeneralArtResponse> getArtListTypeGeneralSearchedByHashtag(String hashtag, String sortType, Pageable pageRequest){
        List<LikeArt> likeArtList = likeArtRepository.findLikeArtsBy();

        switch (sortType) {
            case "date":  // RegisterDateDESC (RD = default)
                return getSortedArtResultTypeGeneral(
                        artRepository.findBySaleTypeGeneralAndHashtagOrderByRegisterDateDesc(hashtag, pageRequest).getContent(),
                        artHashtagRepository.findArtHashtagBy(),
                        likeArtList
                );
            case "rdate":  // RegisterDateASC
                return getSortedArtResultTypeGeneral(
                        artRepository.findBySaleTypeGeneralAndHashtagOrderByRegisterDateAsc(hashtag, pageRequest).getContent(),
                        artHashtagRepository.findArtHashtagBy(),
                        likeArtList
                );
            case "price":  // InitPriceDESC (IP)
                return getSortedArtResultTypeGeneral(
                        artRepository.findBySaleTypeGeneralAndHashtagOrderByInitPriceDesc(hashtag, pageRequest).getContent(),
                        artHashtagRepository.findArtHashtagBy(),
                        likeArtList
                );
            case "rprice":  // InitPriceASC
                return getSortedArtResultTypeGeneral(
                        artRepository.findBySaleTypeGeneralAndHashtagOrderByInitPriceAsc(hashtag, pageRequest).getContent(),
                        artHashtagRepository.findArtHashtagBy(),
                        likeArtList
                );
            case "like":  // LikeArtDESC (LA)
                return getSortedArtResultTypeGeneral(
                        artRepository.findBySaleTypeGeneralAndHashtagOrderByLikeArtCountDesc(hashtag, pageRequest).getContent(),
                        artHashtagRepository.findArtHashtagBy(),
                        likeArtList
                );
            default: // LikeArtASC
                return getSortedArtResultTypeGeneral(
                        artRepository.findBySaleTypeGeneralAndHashtagOrderByLikeArtCountAsc(hashtag, pageRequest).getContent(),
                        artHashtagRepository.findArtHashtagBy(),
                        likeArtList
                );
        }
    }

    private List<GeneralArtResponse> getSortedArtResultTypeGeneral(List<Art> artList, List<ArtHashtag> artHashtagList, List<LikeArt> likeArtList){
        List<GeneralArtResponse> result = new ArrayList<>();
        for (Art art : artList) {
            List<String> hashtagList = getHashtagListFromArt(artHashtagList, art.getId());
            Long likeArtCountByArtId = getLikeArtCountByArtId(likeArtList, art.getId());
            result.add(new GeneralArtResponse(
                    art,
                    hashtagList,
                    likeArtCountByArtId
            ));
        }
        return result;
    }
}
