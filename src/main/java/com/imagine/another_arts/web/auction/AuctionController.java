package com.imagine.another_arts.web.auction;

import com.imagine.another_arts.domain.auctionhistory.service.AuctionHistoryService;
import com.imagine.another_arts.web.auction.dto.BidAuctionRequest;
import com.imagine.another_arts.web.auction.dto.BidAuctionResponse;
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
public class AuctionController {

    private final AuctionHistoryService auctionHistoryService;

    @PostMapping("/bid")
    @ApiOperation(value = "입찰 API", notes = "경매 아이디, 작품 아이디, 유저 아이디, 입찰가를 통해 입찰을 진행")
    public BidAuctionResponse bidAuction(@Valid @ModelAttribute BidAuctionRequest bidAuctionRequest) {
        auctionHistoryService.updateHighestBidDetails(bidAuctionRequest.toServiceDto());
        return new BidAuctionResponse(bidAuctionRequest.getAuctionId(),
                bidAuctionRequest.getUserId(), bidAuctionRequest.getBidPrice());
    }
}
