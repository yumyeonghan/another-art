package com.imagine.another_arts.domain.auction.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BidAuctionRequestDto {
    private Long auctionId;
    private Long userId;
    private Long bidPrice;
}
