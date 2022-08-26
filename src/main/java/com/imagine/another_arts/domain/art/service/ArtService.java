package com.imagine.another_arts.domain.art.service;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.art.enums.SaleType;
import com.imagine.another_arts.domain.art.repository.ArtRepository;
import com.imagine.another_arts.domain.art.service.dto.*;
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
    public Long registerAuctionArt(AuctionArtRegisterRequestDto auctionArtRegisterRequestDto) {
        try {
            Users findArtOwner = userRepository.findById(auctionArtRegisterRequestDto.getUserId())
                    .orElseThrow(() -> new UnAuthenticatedUserException("가입하지 않은 사용자에 대한 작품 등록 권한은 없습니다"));
            MultipartFile file = auctionArtRegisterRequestDto.getFile();
            ArtFileUploadDto fileInfo = getMultipartFileInfo(file);

            Art saveArt = Art.createArt(
                    findArtOwner,
                    auctionArtRegisterRequestDto.getName(),
                    auctionArtRegisterRequestDto.getDescription(),
                    auctionArtRegisterRequestDto.getInitPrice(),
                    SaleType.AUCTION,
                    fileInfo.getUploadName(),
                    fileInfo.getStoregeName()
            );
            validationRegisterArtName(saveArt.getName()); // 작품명 2차 검증
            artRepository.save(saveArt);

            if(auctionArtRegisterRequestDto.getHashtagList() != null) {
                List<String> hashtagList = auctionArtRegisterRequestDto.getHashtagList(); // 해시태그 리스트
                insertHashtagList(hashtagList, saveArt); // [ArtHashtag, Hashtag] 테이블에 값 넣어주기
            }

            Auction saveAuction = Auction.createAuction(
                    auctionArtRegisterRequestDto.getInitPrice(), // 처음에는 InitPrice로 INSERT
                    auctionArtRegisterRequestDto.getStartDate(),
                    auctionArtRegisterRequestDto.getEndDate(),
                    saveArt
            );
            auctionRepository.save(saveAuction);

            file.transferTo(new File(fileDir + fileInfo.getStoregeName())); // 파일 저장
            return saveArt.getId();
        } catch (IOException e) {
            throw new RunTimeArtRegisterException("작품 등록 과정에서 서버상에 오류가 발생했습니다");
        }
    }

    // 일반 작품 등록
    @Transactional
    public Long registerGeneralArt(GeneralArtRegisterRequestDto generalArtRegisterRequestDto) {
        try {
            Users findArtOwner = userRepository.findById(generalArtRegisterRequestDto.getUserId())
                    .orElseThrow(() -> new UnAuthenticatedUserException("가입하지 않은 사용자에 대한 작품 등록 권한은 없습니다"));
            MultipartFile file = generalArtRegisterRequestDto.getFile();
            ArtFileUploadDto fileInfo = getMultipartFileInfo(file);

            Art saveArt = Art.createArt(
                    findArtOwner,
                    generalArtRegisterRequestDto.getName(),
                    generalArtRegisterRequestDto.getDescription(),
                    generalArtRegisterRequestDto.getInitPrice(),
                    SaleType.GENERAL,
                    fileInfo.getUploadName(),
                    fileInfo.getStoregeName()
            );
            validationRegisterArtName(saveArt.getName()); // 작품명 2차 검증
            artRepository.save(saveArt);

            if(generalArtRegisterRequestDto.getHashtagList() != null) {
                List<String> hashtagList = generalArtRegisterRequestDto.getHashtagList(); // 해시태그 리스트
                insertHashtagList(hashtagList, saveArt); // [ArtHashtag, Hashtag] 테이블에 값 넣어주기
            }

            file.transferTo(new File(fileDir + fileInfo.getStoregeName())); // 파일 저장
            return saveArt.getId();
        } catch (IOException e) {
            throw new RunTimeArtRegisterException("작품 등록 과정에서 서버상에 오류가 발생했습니다");
        }
    }

    private void validationRegisterArtName(String artName) {
        if (artRepository.existsByName(artName)) {
            throw new DuplicationArtNameException("작품 이름이 중복됩니다");
        }
    }

    private ArtFileUploadDto getMultipartFileInfo(MultipartFile file) {
        if (file == null) {
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
            Auction findAuction = auctionRepository.findFirstAuctionBy(artId);
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
    public void editArt(Long artId, ArtEditRequestDto artEditRequestDto) {
        Art findArt = artRepository.findById(artId)
                .orElseThrow(() -> new ArtNotFoundException("작품 정보가 존재하지 않습니다"));

        if (StringUtils.hasText(artEditRequestDto.getName())) {
            if (artRepository.existsByIdNotAndName(artId, artEditRequestDto.getName())) {
                throw new IllegalArtModifyException("변경하려는 작품 이름이 이미 존재합니다");
            } else {
                findArt.changeArtName(artEditRequestDto.getName());
            }
        }

        if (StringUtils.hasText(artEditRequestDto.getDescription())) {
            findArt.changeDescription(artEditRequestDto.getDescription());
        }
    }

    // 작품 삭제
    @Transactional
    public void deleteArt(Long artId) {
        Art findArt = artRepository.findById(artId)
                .orElseThrow(() -> new ArtNotFoundException("작품 정보가 존재하지 않습니다"));

        if (purchaseHistoryRepository.existsByArt(findArt)) {
            throw new IllegalArtDeleteException("이미 거래된 작품은 삭제할 수 없습니다");
        } else {
            if (findArt.getSaleType().equals(SaleType.AUCTION)) {
                if (auctionHistoryRepository.existsByArtAndUserIsNotNull(findArt)) {
                    throw new IllegalArtDeleteException("입찰이 한번이라도 진행된 경매 작품은 삭제할 수 없습니다");
                }

                List<ArtHashtag> artHashtagList = artHashtagRepository.findByArtId(artId);
                Auction auction = auctionRepository.findByArt(findArt);

                artHashtagRepository.deleteAll(artHashtagList);
                auctionRepository.delete(auction);
                artRepository.delete(findArt);
            } else {
                List<ArtHashtag> artHashtagList = artHashtagRepository.findByArtId(artId);
                artHashtagRepository.deleteAll(artHashtagList);
                artRepository.delete(findArt);
            }
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

            if (!artHashtagRepository.existsByArtAndHashtag(findArt, hashtag)) { // findArt에 대한 Hashtag가 이미 있는지에 대한 Validation
                artHashtagRepository.save(ArtHashtag.insertArtHashtag(findArt, hashtag)); // 없으면 findArt에 대한 ArtHashtag를 save
            }
        }
    }

    // 해시태그 삭제
    @Transactional
    public void deleteHashtag(Long artId, String name) {
        Art findArt = artRepository.findById(artId)
                .orElseThrow(() -> new ArtNotFoundException("작품 정보가 존재하지 않습니다"));

        Optional<ArtHashtag> findArtHashtag = artHashtagRepository.findByArtIdAndHashtagName(findArt.getId(), name);
        if (findArtHashtag.isEmpty()) {
            throw new IllegalHashtagDeleteException("해시태그가 이미 삭제되거나 없습니다");
        } else {
            artHashtagRepository.delete(findArtHashtag.get());
        }
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
