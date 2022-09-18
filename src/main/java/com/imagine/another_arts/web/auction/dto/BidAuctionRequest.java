package com.imagine.another_arts.web.auction.dto;

import com.imagine.another_arts.domain.auction.service.dto.BidAuctionRequestDto;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class BidAuctionRequest {
    @NotNull(message = "경매 아이디는 필수입니다")
    @ApiParam(value = "경매 아이디", required = true, example = "1")
    private Long auctionId;

    @NotNull(message = "사용자 아이디는 필수입니다")
    @ApiParam(value = "사용자 아이디", required = true, example = "1")
    private Long userId;

    @NotNull(message = "입찰가는 필수입니다")
    @ApiParam(value = "입찰가", required = true, example = "10000")
    private Long bidPrice;

    public BidAuctionRequestDto toServiceDto() {
        return new BidAuctionRequestDto(this.auctionId, this.userId, this.bidPrice);
    }
}
