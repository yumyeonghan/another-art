package com.imagine.another_arts.web.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BidAuctionResponse {
    private Long auctionId;
    private Long userId;
    private Long bidPrice;
}
