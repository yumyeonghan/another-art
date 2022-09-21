package com.imagine.another_arts.web.art.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
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

    @NotNull(message = "페이징 처리를 위한 스크롤은 필수입니다")
    @ApiModelProperty(value = "스크롤 이벤트 발생 시 +1해서 요청", required = true, example = "0")
    private Integer scroll;
}
