package com.imagine.another_arts.domain.art.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.arthashtag.ArtHashtag;
import com.imagine.another_arts.domain.auction.Auction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AuctionRegisterDateDto {
    private ArtSortDto art; // 경매 중인 작품
    private Integer hightesBidPrice; // 경매 최고 비드가
    private UserSortDto highestBidUser; // 경매 최고 비드한 유저
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auctionStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auctionEndDate;

    public AuctionRegisterDateDto(Auction auction, Art art, List<ArtHashtag> artHashtagList){
        this.art = new ArtSortDto(art, artHashtagList);
        this.hightesBidPrice = auction.getBidPrice();
        this.highestBidUser = new UserSortDto(auction.getUser());
        this.auctionStartDate = auction.getStartDate();
        this.auctionEndDate = auction.getEndDate();
    }
}
