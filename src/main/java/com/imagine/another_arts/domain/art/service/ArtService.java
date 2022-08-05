package com.imagine.another_arts.domain.art.service;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.art.repository.ArtRepository;
import com.imagine.another_arts.domain.art.service.dto.AuctionBidCountDto;
import com.imagine.another_arts.domain.art.service.dto.AuctionBidPriceDto;
import com.imagine.another_arts.domain.art.service.dto.AuctionRegisterDateDto;
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
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@SuppressWarnings("unchecked") // 제네릭 Type-UnSafe 제거 (어차피 변환되는 타입 return에 의해서 정해짐)
public class ArtService {
    private final ArtRepository artRepository;
    private final ArtHashtagRepository artHashtagRepository;
    private final AuctionRepository auctionRepository;

    // 기준(sortType)에 따라 정렬된 작품들 return
    public <T> List<T> getSortedAuctionArtList(String sortType, Pageable pageRequest){

        switch (sortType) {
            case "RD":  // RegisterDateDESC (RD = default)
                return (List<T>) getResultOrderByRegisterDate(
                        artRepository.findArtList(),
                        auctionRepository.findAuctionArtSortByRegisterDateDESC(pageRequest).getContent(),
                        artHashtagRepository.findAllArtHashtag()
                );
            case "rRD":  // RegisterDateASC
                return (List<T>) getResultOrderByRegisterDate(
                        artRepository.findArtList(),
                        auctionRepository.findAuctionArtSortByRegisterDateASC(pageRequest).getContent(),
                        artHashtagRepository.findAllArtHashtag()
                );
            case "BP":  // BidPriceDESC (BP)
                return (List<T>) getResultOrderByBidPrice(
                        artRepository.findArtList(),
                        auctionRepository.findAuctionArtSortByBidPriceDESC(pageRequest).getContent(),
                        artHashtagRepository.findAllArtHashtag()
                );
            case "rBP":  // BidPriceASC
                return (List<T>) getResultOrderByBidPrice(
                        artRepository.findArtList(),
                        auctionRepository.findAuctionArtSortByBidPriceASC(pageRequest).getContent(),
                        artHashtagRepository.findAllArtHashtag()
                );
            case "BC":  // BidCountDESC (BC)
                return (List<T>) getResultOrderByBidCount(
                        artRepository.findArtList(),
                        auctionRepository.findAuctionArtSortByBidCountDESC(pageRequest).getContent(),
                        artHashtagRepository.findAllArtHashtag()
                );
            default:
                return (List<T>) getResultOrderByBidCount( // BidCountASC
                        artRepository.findArtList(),
                        auctionRepository.findAuctionArtSortByBidCountASC(pageRequest).getContent(),
                        artHashtagRepository.findAllArtHashtag()
                );
        }
    }

    private List<AuctionRegisterDateDto> getResultOrderByRegisterDate(List<Art> artList, List<Auction> auctionList, List<ArtHashtag> artHashtagList){
        List<AuctionRegisterDateDto> result = new ArrayList<>();
        for (Auction auction : auctionList) {
            result.add(new AuctionRegisterDateDto(
                    auction,
                    getArtByArtId(artList, auction.getArt().getId()),
                    getArtHashtagByArtId(artHashtagList, auction.getArt().getId())
            ));
        }
        return result;
    }

    private List<AuctionBidPriceDto> getResultOrderByBidPrice(List<Art> artList, List<Auction> auctionList, List<ArtHashtag> artHashtagList){
        List<AuctionBidPriceDto> result = new ArrayList<>();
        for (Auction auction : auctionList) {
            result.add(new AuctionBidPriceDto(
                    auction,
                    getArtByArtId(artList, auction.getArt().getId()),
                    getArtHashtagByArtId(artHashtagList, auction.getArt().getId())
            ));
        }
        return result;
    }

    private List<AuctionBidCountDto> getResultOrderByBidCount(List<Art> artList, List<Auction> auctionList, List<ArtHashtag> artHashtagList){
        List<AuctionBidCountDto> result = new ArrayList<>();
        for (Auction auction : auctionList) {
            result.add(new AuctionBidCountDto(
                    auction,
                    getArtByArtId(artList, auction.getArt().getId()),
                    getArtHashtagByArtId(artHashtagList, auction.getArt().getId())
            ));
        }
        return result;
    }

    private Art getArtByArtId(List<Art> artList, Long artId){
        return artList.stream()
                .filter(art -> Objects.equals(art.getId(), artId))
                .findFirst()
                .orElseThrow(() -> new ArtNotFoundException("작품을 찾지 못했습니다"));
    }

    private List<ArtHashtag> getArtHashtagByArtId(List<ArtHashtag> artHashtagList, Long artId){
        return artHashtagList.stream()
                .filter(artHashtag -> artHashtag.getArt().getId().equals(artId))
                .collect(Collectors.toList());
    }
}
