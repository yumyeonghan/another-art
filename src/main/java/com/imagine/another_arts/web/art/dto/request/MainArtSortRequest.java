package com.imagine.another_arts.web.art.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MainArtSortRequest {
    @ApiModelProperty(value = "정렬 기준 [date, rdate, price, rprice, count, rcount]", required = true, example = "date")
    private String sort;

    @ApiModelProperty(value = "스크롤 이벤트 발생 시 +1해서 요청", required = true, example = "0")
    private Integer scroll;
}
