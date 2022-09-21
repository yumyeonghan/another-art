package com.imagine.another_arts.web.purchase.dto.request;

import com.imagine.another_arts.domain.purchase.service.dto.request.PurchaseAuctionArtRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseAuctionArtRequest {
    @NotNull(message = "경매 ID는 필수입니다")
    @ApiModelProperty(value = "경매 ID", required = true, example = "1")
    private Long auctionId;

    @NotNull(message = "사용자 ID는 필수입니다")
    @ApiModelProperty(value = "사용자 ID", required = true, example = "1")
    private Long userId;

    public PurchaseAuctionArtRequestDto toServiceDto() {
        return new PurchaseAuctionArtRequestDto(this.auctionId, this.userId);
    }
}
