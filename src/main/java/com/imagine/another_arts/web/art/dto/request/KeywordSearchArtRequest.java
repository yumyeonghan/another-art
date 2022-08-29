package com.imagine.another_arts.web.art.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeywordSearchArtRequest {
    @ApiModelProperty(value = "검색 키워드 (단어 하나)", required = true)
    private String keyword;

    @ApiModelProperty(value = "작품 타입 [auction/general]", required = true)
    private String type;

    @ApiModelProperty(value = "정렬 기준 [AUCTION = date, rdate, price, rprice, count, rcount / GENERAL = date, rdate, price, rprice, like, rlike]", required = true)
    private String sort;

    @ApiModelProperty(value = "스크롤 이벤트 발생 시 +1해서 요청", required = true, example = "0")
    private Integer scroll;
}
