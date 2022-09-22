package com.imagine.another_arts.domain.purchase.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PurchaseAuctionArtRequestDto {
    private Long auctionId;
    private Long userId;
}
