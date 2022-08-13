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
    public List<AuctionArtResponseDto> getSortedAuctionArtList(String sortType, Pageable pageRequest){
        List<Art> auctionArtList = artRepository.findAuctionArt();
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtag();

        switch (sortType) {
            case "date":  // RegisterDateDESC (RD = default)
                return getAuctionArtSortedResult(
                        auctionArtList,
                        auctionRepository.findAuctionArtSortByRegisterDateDESC(pageRequest).getContent(),
                        artHashtagList
                );
            case "rdate":  // RegisterDateASC
                return getAuctionArtSortedResult(
                        auctionArtList,
                        auctionRepository.findAuctionArtSortByRegisterDateASC(pageRequest).getContent(),
                        artHashtagList
                );
            case "price":  // BidPriceDESC (BP)
                return getAuctionArtSortedResult(
                        auctionArtList,
                        auctionRepository.findAuctionArtSortByBidPriceDESC(pageRequest).getContent(),
                        artHashtagList
                );
            case "rprice":  // BidPriceASC
                return getAuctionArtSortedResult(
                        auctionArtList,
                        auctionRepository.findAuctionArtSortByBidPriceASC(pageRequest).getContent(),
                        artHashtagList
                );
            case "count":  // BidCountDESC (BC)
                return getAuctionArtSortedResult(
                        auctionArtList,
                        auctionRepository.findAuctionArtSortByBidCountDESC(pageRequest).getContent(),
                        artHashtagList
                );
            default: // BidCountASC
                return getAuctionArtSortedResult(
                        auctionArtList,
                        auctionRepository.findAuctionArtSortByBidCountASC(pageRequest).getContent(),
                        artHashtagList
                );
        }
    }

    private List<AuctionArtResponseDto> getAuctionArtSortedResult(List<Art> artList, List<Auction> auctionList, List<ArtHashtag> artHashtagList){
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
    public List<AuctionArtResponseDto> getSearchedAuctionArtList(String hashtag, String sortType, Pageable pageRequest){
        List<Art> auctionArtList = artRepository.findAuctionArt();
        List<ArtHashtag> artHashtagList = artHashtagRepository.findArtHashtag();

        switch (sortType) {
            case "date":  // RegisterDateDESC (RD = default)
                return getAuctionArtSortedResult(
                        auctionArtList,
                        auctionRepository.findAuctionArtSortByRegisterDateDESCWithHashtag(hashtag, pageRequest).getContent(),
                        artHashtagList
                );
            case "rdate":  // RegisterDateASC
                return getAuctionArtSortedResult(
                        auctionArtList,
                        auctionRepository.findAuctionArtSortByRegisterDateASCWithHashtag(hashtag, pageRequest).getContent(),
                        artHashtagList
                );
            case "price":  // BidPriceDESC (BP)
                return getAuctionArtSortedResult(
                        auctionArtList,
                        auctionRepository.findAuctionArtSortByBidPriceDESCWithHashtag(hashtag, pageRequest).getContent(),
                        artHashtagList
                );
            case "rprice":  // BidPriceASC
                return getAuctionArtSortedResult(
                        auctionArtList,
                        auctionRepository.findAuctionArtSortByBidPriceASCWithHashtag(hashtag, pageRequest).getContent(),
                        artHashtagList
                );
            case "count":  // BidCountDESC (BC)
                return getAuctionArtSortedResult(
                        auctionArtList,
                        auctionRepository.findAuctionArtSortByBidCountDESCWithHashtag(hashtag, pageRequest).getContent(),
                        artHashtagList
                );
            default: // BidCountASC
                return getAuctionArtSortedResult(
                        auctionArtList,
                        auctionRepository.findAuctionArtSortByBidCountASCWithHashtag(hashtag, pageRequest).getContent(),
                        artHashtagList
                );
        }
    }

    // 해시태그 기반 "일반 작품" 검색
    public List<GeneralArtResponseDto> getSearchedGeneralArtList(String hashtag, String sortType, Pageable pageRequest){
        switch (sortType) {
            case "date":  // RegisterDateDESC (RD = default)
                return getGeneralArtSortedResult(
                        artRepository.findGeneralArtSortByRegisterDateDESCWithHashtag(hashtag, pageRequest).getContent()
                );
            case "rdate":  // RegisterDateASC
                return getGeneralArtSortedResult(
                        artRepository.findGeneralArtSortByRegisterDateASCWithHashtag(hashtag, pageRequest).getContent()
                );
            case "price":  // InitPriceDESC (IP)
                return getGeneralArtSortedResult(
                        artRepository.findGeneralArtSortByInitPriceDESCWithHashtag(hashtag, pageRequest).getContent()
                );
            case "rprice":  // InitPriceASC
                return getGeneralArtSortedResult(
                        artRepository.findGeneralArtSortByInitPriceASCWithHashtag(hashtag, pageRequest).getContent()
                );
            case "like":  // LikeArtDESC (LA)
                return getGeneralArtSortedResult(
                        artRepository.findGeneralArtSortByLikeArtDESCWithHashtag(hashtag, pageRequest).getContent()
                );
            default: // LikeArtASC
                return getGeneralArtSortedResult(
                        artRepository.findGeneralArtSortByLikeArtASCWithHashtag(hashtag, pageRequest).getContent()
                );
        }
    }

    private List<GeneralArtResponseDto> getGeneralArtSortedResult(List<Art> artList){
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
