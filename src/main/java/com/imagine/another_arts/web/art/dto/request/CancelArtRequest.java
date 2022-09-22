package com.imagine.another_arts.web.art.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CancelArtRequest {
    @NotNull(message = "작품 ID는 필수입니다")
    @ApiModelProperty(value = "좋아요를 취소할 작품 ID", required = true)
    private Long artId;

    @NotNull(message = "사용자 ID는 필수입니다")
    @ApiModelProperty(value = "좋아요 취소 버튼을 누르는 사용자 ID", required = true)
    private Long userId;
}
