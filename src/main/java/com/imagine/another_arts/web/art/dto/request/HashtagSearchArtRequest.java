package com.imagine.another_arts.web.art.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HashtagSearchArtRequest {
    @ApiModelProperty(name = "hashtag", value = "검색 해시태그", required = true)
    private String hashtag;

    @ApiModelProperty(name = "type", value = "작품 타입 [auction/general]", required = true)
    private String type;

    @ApiModelProperty(name = "sort", value = "정렬 기준 [AUCTION = date, rdate, price, rprice, count, rcount / GENERAL = date, rdate, price, rprice, like, rlike]", required = true)
    private String sort;

    @ApiModelProperty(name = "scroll", value = "스크롤 이벤트 발생 시 +1해서 요청", required = true, example = "0")
    private Integer scroll;
}
