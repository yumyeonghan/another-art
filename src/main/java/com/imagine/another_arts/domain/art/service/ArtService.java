package com.imagine.another_arts.domain.art.service;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.art.repository.ArtRepository;
import com.imagine.another_arts.domain.art.service.dto.SortedAuctionArtDto;
import com.imagine.another_arts.domain.arthashtag.ArtHashtag;
import com.imagine.another_arts.domain.arthashtag.repository.ArtHashtagRepository;
import com.imagine.another_arts.domain.auction.Auction;
import com.imagine.another_arts.domain.auction.repository.AuctionRepository;
import com.imagine.another_arts.exception.ArtNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtService {
    private final ArtRepository artRepository;
    private final ArtHashtagRepository artHashtagRepository;
    private final AuctionRepository auctionRepository;

    // 기준(sortType)에 따라 정렬된 작품들 return
    public List<SortedAuctionArtDto> getSortedAuctionArtList(String sortType, Pageable pageRequest){

        switch (sortType) {
            case "RD":  // RegisterDateDESC (RD = default)
                return getSortedResult(
                        artRepository.findAuctionArtList(),
                        auctionRepository.findAuctionArtSortByRegisterDateDESC(LocalDateTime.now(), pageRequest).getContent(),
                        artHashtagRepository.findArtHashtag()
                );
            case "rRD":  // RegisterDateASC
                return getSortedResult(
                        artRepository.findAuctionArtList(),
                        auctionRepository.findAuctionArtSortByRegisterDateASC(LocalDateTime.now(), pageRequest).getContent(),
                        artHashtagRepository.findArtHashtag()
                );
            case "BP":  // BidPriceDESC (BP)
                return getSortedResult(
                        artRepository.findAuctionArtList(),
                        auctionRepository.findAuctionArtSortByBidPriceDESC(LocalDateTime.now(), pageRequest).getContent(),
                        artHashtagRepository.findArtHashtag()
                );
            case "rBP":  // BidPriceASC
                return getSortedResult(
                        artRepository.findAuctionArtList(),
                        auctionRepository.findAuctionArtSortByBidPriceASC(LocalDateTime.now(), pageRequest).getContent(),
                        artHashtagRepository.findArtHashtag()
                );
            case "BC":  // BidCountDESC (BC)
                return getSortedResult(
                        artRepository.findAuctionArtList(),
                        auctionRepository.findAuctionArtSortByBidCountDESC(LocalDateTime.now(), pageRequest).getContent(),
                        artHashtagRepository.findArtHashtag()
                );
            default:
                return getSortedResult( // BidCountASC
                        artRepository.findAuctionArtList(),
                        auctionRepository.findAuctionArtSortByBidCountASC(LocalDateTime.now(), pageRequest).getContent(),
                        artHashtagRepository.findArtHashtag()
                );
        }
    }

    private List<SortedAuctionArtDto> getSortedResult(List<Art> artList, List<Auction> auctionList, List<ArtHashtag> artHashtagList){
        List<SortedAuctionArtDto> result = new ArrayList<>();
        for (Auction auction : auctionList) {
            result.add(new SortedAuctionArtDto(
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
