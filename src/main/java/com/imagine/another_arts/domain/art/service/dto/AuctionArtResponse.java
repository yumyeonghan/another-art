package com.imagine.another_arts.domain.art.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.auction.Auction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class AuctionArtResponse {
    private Long auctionId; // 경매 ID(PK)
    private Long bidCount; // 비드 횟수
    private ArtInfoDto art; // 경매 중인 작품
    private Integer hightesBidPrice; // 경매 최고 비드가
    private ArtOwnerDto highestBidUser; // 경매 최고 비드한 유저
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auctionStartDate; // 경매 시작날짜
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auctionEndDate; // 경매 종료날짜

    public AuctionArtResponse(Auction auction, Art art, List<String> hashtag, Long bidCount){
        this.auctionId = auction.getId();
        this.bidCount = bidCount;
        this.art = new ArtInfoDto(art, hashtag);
        this.hightesBidPrice = auction.getBidPrice();
        if (Optional.ofNullable(auction.getUser()).isEmpty()) { // 아직 비드하지 않은 경매 작품
            this.highestBidUser = null;
        } else {
            this.highestBidUser = new ArtOwnerDto(auction.getUser());
        }
        this.auctionStartDate = auction.getStartDate();
        this.auctionEndDate = auction.getEndDate();
    }
}
