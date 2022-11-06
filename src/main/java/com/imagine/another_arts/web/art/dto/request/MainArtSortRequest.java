package com.imagine.another_arts.web.art.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MainArtSortRequest {
    @NotBlank(message = "정렬 기준은 필수입니다")
    @ApiModelProperty(value = "정렬 기준 [date, rdate, price, rprice, count, rcount]", required = true, example = "date")
    private String sort;

    @NotNull(message = "현재 페이지 번호는 필수입니다")
    @ApiModelProperty(value = "현재 페이지 번호", required = true, example = "1")
    private Integer page;
}
