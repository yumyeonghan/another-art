package com.imagine.another_arts.web.auction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BidAuctionResponse {
    private Long auctionId;
    private Long userId;
    private Long bidPrice;
}
