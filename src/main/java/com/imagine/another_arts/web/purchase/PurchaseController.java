package com.imagine.another_arts.web.purchase;

import com.imagine.another_arts.domain.purchase.service.PurchaseHistoryService;
import com.imagine.another_arts.web.purchase.dto.PurchaseArtRequest;
import com.imagine.another_arts.web.purchase.dto.PurchaseArtResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PurchaseController {

    private final PurchaseHistoryService purchaseHistoryService;

    @PostMapping("/purchase")
    @ApiOperation(value = "구매 API", notes = "작품 아이디, 유저 아이디를 통한 일반 작품, 경매 작품 구매 진행 / 포인트 사용가능 포인트, 포인트 기록, 구매 기록 처리")
    public PurchaseArtResponse purchaseArt(@Valid @ModelAttribute PurchaseArtRequest purchaseArtRequest) {

        purchaseHistoryService.savePurchaseHistory(purchaseArtRequest.toServiceDto());

        return new PurchaseArtResponse(
                purchaseArtRequest.getArtId(),
                purchaseArtRequest.getUserId());
    }
}
