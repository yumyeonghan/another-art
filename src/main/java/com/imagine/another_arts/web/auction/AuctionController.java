package com.imagine.another_arts.web.auction;

import com.imagine.another_arts.domain.auction.service.AuctionService;
import com.imagine.another_arts.web.auction.dto.request.BidAuctionRequest;
import com.imagine.another_arts.web.auction.dto.response.BidAuctionResponse;
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
public class AuctionController {
    private final AuctionService auctionService;

    @PostMapping("/bid")
    @ApiOperation(value = "입찰 API", notes = "경매 ID, 유저 ID, 입찰가를 통해 입찰을 진행")
    public BidAuctionResponse bidAuction(@Valid @RequestBody BidAuctionRequest bidAuctionRequest) {
        auctionService.updateHighestBidDetails(bidAuctionRequest.toServiceDto());
        return new BidAuctionResponse(
                bidAuctionRequest.getAuctionId(),
                bidAuctionRequest.getUserId(),
                bidAuctionRequest.getBidPrice()
        );
    }
}
