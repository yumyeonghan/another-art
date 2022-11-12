package com.imagine.another_arts.web.purchase.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseGeneralArtRequest {
    @NotNull(message = "작품 ID는 필수입니다")
    @ApiModelProperty(value = "작품 ID", required = true, example = "1")
    private Long artId;

    @NotNull(message = "사용자 ID는 필수입니다")
    @ApiModelProperty(value = "사용자 ID", required = true, example = "1")
    private Long userId;
}
