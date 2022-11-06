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
public class KeywordSearchArtRequest {
    @NotBlank(message = "검색 키워드(단어 하나)는 필수입니다")
    @ApiModelProperty(value = "검색 키워드 (단어 하나)", required = true)
    private String keyword;

    @NotBlank(message = "작품 타입은 필수입니다")
    @ApiModelProperty(value = "작품 타입 [auction/general]", required = true)
    private String type;

    @NotBlank(message = "정렬 기준은 필수입니다")
    @ApiModelProperty(value = "정렬 기준 [AUCTION = date, rdate, price, rprice, count, rcount / GENERAL = date, rdate, price, rprice, like, rlike]", required = true)
    private String sort;

    @NotNull(message = "현재 페이지 번호는 필수입니다")
    @ApiModelProperty(value = "현재 페이지 번호", required = true, example = "1")
    private Integer page;
}
