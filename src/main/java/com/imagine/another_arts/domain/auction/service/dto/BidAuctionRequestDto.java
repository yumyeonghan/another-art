package com.imagine.another_arts.domain.auction.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BidAuctionRequestDto {
    private Long auctionId;
    private Long userId;
    private Long bidPrice;
}
