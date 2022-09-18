package com.imagine.another_arts.domain.auction.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BidAuctionResponseDto {
    private boolean successResponse;
    private String artName;
    private Long userId;
    private Long bidPrice;
}
