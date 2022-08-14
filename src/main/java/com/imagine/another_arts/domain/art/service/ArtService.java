package com.imagine.another_arts.domain.art.service;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.art.repository.ArtRepository;
import com.imagine.another_arts.domain.art.service.dto.AuctionArtResponseDto;
import com.imagine.another_arts.domain.art.service.dto.GeneralArtResponseDto;
import com.imagine.another_arts.domain.arthashtag.ArtHashtag;
import com.imagine.another_arts.domain.arthashtag.repository.ArtHashtagRepository;
import com.imagine.another_arts.domain.auction.Auction;
import com.imagine.another_arts.domain.auction.repository.AuctionRepository;
import com.imagine.another_arts.exception.ArtNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtService {
    private final ArtRepository artRepository;
    private final ArtHashtagRepository artHashtagRepository;
    private final AuctionRepository auctionRepository;

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
