package com.imagine.another_arts.web.art.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtEditRequest {
    @ApiParam(value = "변경할 작품 이름")
    private String name;

    @ApiParam(value = "변경할 작품 설명")
    private String description;
}
