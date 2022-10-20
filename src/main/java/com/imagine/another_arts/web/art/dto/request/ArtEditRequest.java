package com.imagine.another_arts.web.art.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArtEditRequest {
    @ApiModelProperty(value = "변경할 작품 이름")
    private String updateName;

    @ApiModelProperty(value = "변경할 작품 설명")
    private String updateDescription;
}
