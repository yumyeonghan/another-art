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
public class ArtMainSearchRequest {
    @NotBlank(message = "정렬 기준은 필수입니다")
    @ApiModelProperty(value = "정렬 기준 [date, rdate, price, rprice, count, rcount]", required = true, example = "date")
    private String sort;

    @NotNull(message = "페이징 처리를 위한 스크롤은 필수입니다")
    @ApiModelProperty(value = "스크롤 이벤트 발생 시 +1해서 요청", required = true, example = "0")
    private Integer scroll;
}
