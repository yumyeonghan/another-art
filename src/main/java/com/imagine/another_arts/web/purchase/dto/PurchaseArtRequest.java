package com.imagine.another_arts.web.purchase.dto;

import com.imagine.another_arts.domain.auction.service.dto.BidAuctionRequestDto;
import com.imagine.another_arts.domain.purchase.service.dto.PurchaseArtRequestDto;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class PurchaseArtRequest {
    @NotNull(message = "작품 아이디는 필수입니다")
    @ApiParam(value = "작품 아이디", required = true, example = "1")
    private Long artId;

    @NotNull(message = "사용자 아이디는 필수입니다")
    @ApiParam(value = "사용자 아이디", required = true, example = "1")
    private Long userId;

    public PurchaseArtRequestDto toServiceDto() {
        return new PurchaseArtRequestDto(this.artId, this.userId);
    }
}
