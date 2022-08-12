package com.imagine.another_arts.domain.art.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.auction.Auction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AuctionArtResponseDto {
    private Long auctionId; // 경매 ID(PK)
    private Integer bidCount; // 비드 횟수
    private ArtInfoDto art; // 경매 중인 작품
    private Integer hightesBidPrice; // 경매 최고 비드가
    private ArtUserDto highestBidUser; // 경매 최고 비드한 유저
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auctionStartDate; // 경매 시작날짜
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auctionEndDate; // 경매 종료날짜

    public AuctionArtResponseDto(Auction auction, Art art, List<String> hashtag){
        this.auctionId = auction.getId();
        this.bidCount = auction.getAuctionHistoryList().size();
        this.art = new ArtInfoDto(art, hashtag);
        this.hightesBidPrice = auction.getBidPrice();
        this.highestBidUser = new ArtUserDto(auction.getUser());
        this.auctionStartDate = auction.getStartDate();
        this.auctionEndDate = auction.getEndDate();
    }
}
