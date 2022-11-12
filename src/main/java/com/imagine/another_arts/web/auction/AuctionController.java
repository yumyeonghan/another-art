package com.imagine.another_arts.web.auction;

import com.imagine.another_arts.domain.auction.service.AuctionService;
import com.imagine.another_arts.web.auction.dto.request.AuctionBidRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Api(tags = "경매 API")
public class AuctionController {
    private final AuctionService auctionService;

    @PostMapping("/bid")
    @ApiOperation(value = "입찰 API", notes = "경매 ID, 유저 ID, 입찰가를 통해 입찰을 진행")
    public ResponseEntity<Void> bidAuction(@Valid @RequestBody AuctionBidRequest bidRequest) {
        auctionService.updateHighestBidDetails(bidRequest.getAuctionId(), bidRequest.getUserId(), bidRequest.getBidPrice());
        return ResponseEntity.noContent().build();
    }
}
