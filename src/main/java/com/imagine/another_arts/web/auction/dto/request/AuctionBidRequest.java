package com.imagine.another_arts.web.auction.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionBidRequest {
    @NotNull(message = "경매 아이디는 필수입니다")
    @ApiModelProperty(value = "경매 아이디", required = true, example = "1")
    private Long auctionId;

    @NotNull(message = "사용자 아이디는 필수입니다")
    @ApiModelProperty(value = "사용자 아이디", required = true, example = "1")
    private Long userId;

    @NotNull(message = "입찰가는 필수입니다")
    @ApiModelProperty(value = "입찰가", required = true, example = "1000")
    private Long bidPrice;
}
