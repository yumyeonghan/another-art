package com.imagine.another_arts.web.point.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PointUseRequest {
    @NotBlank(message = "사용자 로그인 아이디는 필수입니다")
    @ApiModelProperty(value = "사용자 로그인 아이디", required = true)
    private String loginId;

    @NotNull(message = "충전/환불 금액은 필수입니다")
    @ApiModelProperty(value = "충전/환불 금액", required = true)
    private Long dealAmount;
}
