package com.imagine.another_arts.web.purchase;

import com.imagine.another_arts.domain.purchase.service.PurchaseHistoryService;
import com.imagine.another_arts.web.purchase.dto.request.PurchaseAuctionArtRequest;
import com.imagine.another_arts.web.purchase.dto.request.PurchaseGeneralArtRequest;
import com.imagine.another_arts.web.purchase.dto.response.PurchaseArtResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PurchaseController {

    private final PurchaseHistoryService purchaseHistoryService;

    @PostMapping("/purchase/auction")
    @ApiOperation(value = "경매 작품 구매 API", notes = "경매 ID, 사용자 ID를 통한 낙찰된 경매 작품 구매 API")
    public PurchaseArtResponse purchaseAuctionArt(@Valid @RequestBody PurchaseAuctionArtRequest purchaseAuctionArtRequest) {
        Long purchaseHistoryId = purchaseHistoryService.purchaseAuctionArt(purchaseAuctionArtRequest.toServiceDto());
        return new PurchaseArtResponse(purchaseHistoryId);
    }

    @PostMapping("/purchase/general")
    @ApiOperation(value = "일반 작품 구매 API", notes = "작품 ID, 사용자 ID를 통한 일반 작품 구매 API")
    public PurchaseArtResponse purchaseGeneralArt(@Valid @RequestBody PurchaseGeneralArtRequest purchaseGeneralArtRequest) {
        Long purchaseHistoryId = purchaseHistoryService.purchaseGeneralArt(purchaseGeneralArtRequest.toServiceDto());
        return new PurchaseArtResponse(purchaseHistoryId);
    }
}
