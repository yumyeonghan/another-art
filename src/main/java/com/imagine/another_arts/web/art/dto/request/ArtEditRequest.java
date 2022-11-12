package com.imagine.another_arts.web.art.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArtEditRequest {
    @ApiModelProperty(value = "변경할 작품 이름", example = "Test")
    private String updateName;

    @ApiModelProperty(value = "변경할 작품 설명", example = "작품 테스트입니다")
    private String updateDescription;
}
