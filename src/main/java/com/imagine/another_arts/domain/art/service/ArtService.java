package com.imagine.another_arts.domain.art.service;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.art.enums.SaleType;
import com.imagine.another_arts.domain.art.repository.ArtRepository;
import com.imagine.another_arts.domain.art.service.dto.AuctionArtResponseDto;
import com.imagine.another_arts.domain.art.service.dto.GeneralArtResponseDto;
import com.imagine.another_arts.domain.arthashtag.ArtHashtag;
import com.imagine.another_arts.domain.arthashtag.repository.ArtHashtagRepository;
import com.imagine.another_arts.domain.auction.Auction;
import com.imagine.another_arts.domain.auction.repository.AuctionRepository;
import com.imagine.another_arts.domain.hashtag.Hashtag;
import com.imagine.another_arts.domain.hashtag.repository.HashtagRepository;
import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.ArtNotFoundException;
import com.imagine.another_arts.exception.IllegalArtFileUploadException;
import com.imagine.another_arts.exception.RunTimeArtRegisterException;
import com.imagine.another_arts.exception.UserNotFoundException;
import com.imagine.another_arts.web.art.dto.AuctionArtRegisterForm;
import com.imagine.another_arts.web.art.dto.GeneralArtRegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtService {
    private final UserRepository userRepository;
    private final ArtRepository artRepository;
    private final AuctionRepository auctionRepository;
    private final ArtHashtagRepository artHashtagRepository;
    private final HashtagRepository hashtagRepository;

    @Value("${file.dir}")
    private String fileDir;

    @Transactional
    public void registerAuctionArt(AuctionArtRegisterForm auctionArtRegisterForm) {
        try {
            Users findArtOwner = userRepository.findById(auctionArtRegisterForm.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다"));
            MultipartFile file = auctionArtRegisterForm.getFile();

            if (file.isEmpty()) {
                throw new IllegalArtFileUploadException("파일이 업로드되지 않았습니다");
            }

            String uploadName = file.getOriginalFilename();
            assert uploadName != null;
            String storageName = generateServerStorageName(uploadName);

            Art saveArt = Art.createArt(
                    findArtOwner,
                    auctionArtRegisterForm.getName(),
                    auctionArtRegisterForm.getDescription(),
                    auctionArtRegisterForm.getInitPrice(),
                    SaleType.AUCTION,
                    uploadName,
                    storageName
            );

            List<String> hashtagList = auctionArtRegisterForm.getHashtagList(); // 해시태그 리스트
            for (String name : hashtagList) {
                saveArt.getHashtagList().add(name);
            }
            artRepository.save(saveArt);

            for (String name : hashtagList) {
                Hashtag hashtag;
                Optional<Hashtag> findHashtag = hashtagRepository.findFirstByName(name);
                if (findHashtag.isPresent()) { // 이미 해시태그가 DB상에 존재
                    hashtag = findHashtag.get();
                } else { // 해시태그가 DB상에 없을 경우
                    hashtag = Hashtag.createHashtag(name);
                    hashtagRepository.save(hashtag);
                }

                ArtHashtag artHashtag = ArtHashtag.insertArtHashtag(saveArt, hashtag);
                artHashtagRepository.save(artHashtag);
            }

            Auction saveAuction = Auction.createAuction(
                    auctionArtRegisterForm.getInitPrice(), // bid 시작가로 INSERT
                    auctionArtRegisterForm.getStartDate(),
                    auctionArtRegisterForm.getEndDate(),
                    saveArt
            );
            auctionRepository.save(saveAuction);

            file.transferTo(new File(fileDir + storageName)); // 파일 저장
        } catch (IOException e) {
            throw new RunTimeArtRegisterException("작품 등록 과정에서 서버상에 오류가 발생했습니다");
        }
    }

    @Transactional
    public void registerGeneralArt(GeneralArtRegisterForm generalArtRegisterForm) {
        try {
            Users artUser = userRepository.findById(generalArtRegisterForm.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("회원 정보가 존재하지 않습니다"));
            MultipartFile file = generalArtRegisterForm.getFile();

            if (file.isEmpty()) {
                throw new IllegalArtFileUploadException("파일이 업로드되지 않았습니다");
            }

            String uploadName = file.getOriginalFilename();
            assert uploadName != null;
            String storageName = generateServerStorageName(uploadName);

            Art saveArt = Art.createArt(
                    artUser,
                    generalArtRegisterForm.getName(),
                    generalArtRegisterForm.getDescription(),
                    generalArtRegisterForm.getInitPrice(),
                    SaleType.GENERAL,
                    uploadName,
                    storageName
            );
            
            List<String> hashtagList = generalArtRegisterForm.getHashtagList(); // 해시태그 리스트
            for (String name : hashtagList) {
                saveArt.getHashtagList().add(name);
            }
            artRepository.save(saveArt);

            for (String name : hashtagList) {
                Hashtag hashtag;
                Optional<Hashtag> findHashtag = hashtagRepository.findFirstByName(name);
                if (findHashtag.isPresent()) { // 이미 해시태그가 DB상에 존재
                    hashtag = findHashtag.get();
                } else { // 해시태그가 DB상에 없을 경우
                    hashtag = Hashtag.createHashtag(name);
                    hashtagRepository.save(hashtag);
                }

                ArtHashtag artHashtag = ArtHashtag.insertArtHashtag(saveArt, hashtag);
                artHashtagRepository.save(artHashtag);
            }

            file.transferTo(new File(fileDir + storageName)); // 파일 저장
        } catch (IOException e) {
            throw new RunTimeArtRegisterException("작품 등록 과정에서 서버상에 오류가 발생했습니다");
        }
    }

    private String generateServerStorageName(String uploadName){
        String ext = uploadName.substring(uploadName.lastIndexOf(".") + 1);
        String name = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        return name + "." + ext;
    }

    // 메인페이지 정렬 기준에 따른 "경매 작품" 정렬
    public List<AuctionArtResponseDto> getArtListTypeAuction(String sortType, Pageable pageRequest){
        List<Art> auctionArtList = artRepository.findBySaleTypeAuction();
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtag();

        switch (sortType) {
            case "date":  // RegisterDateDESC (RD = default)
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionOrderByRegisterDateDesc(pageRequest).getContent(),
                        artHashtagList
                );
            case "rdate":  // RegisterDateASC
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionOrderByRegisterDateAsc(pageRequest).getContent(),
                        artHashtagList
                );
            case "price":  // BidPriceDESC (BP)
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionOrderByBidPriceDesc(pageRequest).getContent(),
                        artHashtagList
                );
            case "rprice":  // BidPriceASC
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionOrderByBidPriceAsc(pageRequest).getContent(),
                        artHashtagList
                );
            case "count":  // BidCountDESC (BC)
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionOrderByAuctionHistoryCountDesc(pageRequest).getContent(),
                        artHashtagList
                );
            default: // BidCountASC
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionOrderByAuctionHistoryCountAsc(pageRequest).getContent(),
                        artHashtagList
                );
        }
    }

    private List<AuctionArtResponseDto> getSortedArtResultTypeAuction(List<Art> artList, List<Auction> auctionList, List<ArtHashtag> artHashtagList){
        List<AuctionArtResponseDto> result = new ArrayList<>();
        for (Auction auction : auctionList) {
            Art auctionArt = getArtByArtId(artList, auction.getArt().getId());
            List<String> hashtagList = getHashtagListFromArt(artHashtagList, auctionArt.getId());
            result.add(new AuctionArtResponseDto(
                    auction,
                    auctionArt,
                    hashtagList
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
    public List<AuctionArtResponseDto> getArtListTypeAuctionSearchedByHashtag(String hashtag, String sortType, Pageable pageRequest){
        List<Art> auctionArtList = artRepository.findBySaleTypeAuction();
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtag();

        switch (sortType) {
            case "date":  // RegisterDateDESC (RD = default)
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionAndHashtagOrderByRegisterDateDesc(hashtag, pageRequest).getContent(),
                        artHashtagList
                );
            case "rdate":  // RegisterDateASC
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionAndHashtagOrderByRegisterDateAsc(hashtag, pageRequest).getContent(),
                        artHashtagList
                );
            case "price":  // BidPriceDESC (BP)
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionAndHashtagOrderByBidPriceDesc(hashtag, pageRequest).getContent(),
                        artHashtagList
                );
            case "rprice":  // BidPriceASC
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionAndHashtagOrderByBidPriceAsc(hashtag, pageRequest).getContent(),
                        artHashtagList
                );
            case "count":  // BidCountDESC (BC)
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionAndHashtagOrderByAuctionHistoryCountDesc(hashtag, pageRequest).getContent(),
                        artHashtagList
                );
            default: // BidCountASC
                return getSortedArtResultTypeAuction(
                        auctionArtList,
                        auctionRepository.findBySaleTypeAuctionAndHashtagOrderByAuctionHistoryCountAsc(hashtag, pageRequest).getContent(),
                        artHashtagList
                );
        }
    }

    // 해시태그 기반 "일반 작품" 검색
    public List<GeneralArtResponseDto> getArtListTypeGeneralSearchedByHashtag(String hashtag, String sortType, Pageable pageRequest){
        switch (sortType) {
            case "date":  // RegisterDateDESC (RD = default)
                return getSortedArtResultTypeGeneral(
                        artRepository.findBySaleTypeGeneralAndHashtagOrderByRegisterDateDesc(hashtag, pageRequest).getContent()
                );
            case "rdate":  // RegisterDateASC
                return getSortedArtResultTypeGeneral(
                        artRepository.findBySaleTypeGeneralAndHashtagOrderByRegisterDateAsc(hashtag, pageRequest).getContent()
                );
            case "price":  // InitPriceDESC (IP)
                return getSortedArtResultTypeGeneral(
                        artRepository.findBySaleTypeGeneralAndHashtagOrderByInitPriceDesc(hashtag, pageRequest).getContent()
                );
            case "rprice":  // InitPriceASC
                return getSortedArtResultTypeGeneral(
                        artRepository.findBySaleTypeGeneralAndHashtagOrderByInitPriceAsc(hashtag, pageRequest).getContent()
                );
            case "like":  // LikeArtDESC (LA)
                return getSortedArtResultTypeGeneral(
                        artRepository.findBySaleTypeGeneralAndHashtagOrderByLikeArtCountDesc(hashtag, pageRequest).getContent()
                );
            default: // LikeArtASC
                return getSortedArtResultTypeGeneral(
                        artRepository.findBySaleTypeGeneralAndHashtagOrderByLikeArtCountAsc(hashtag, pageRequest).getContent()
                );
        }
    }

    private List<GeneralArtResponseDto> getSortedArtResultTypeGeneral(List<Art> artList){
        List<GeneralArtResponseDto> result = new ArrayList<>();
        for (Art art : artList) {
            result.add(new GeneralArtResponseDto(
                    art,
                    art.getHashtagList()
            ));
        }
        return result;
    }
}
