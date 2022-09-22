package com.imagine.another_arts.web.art.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DuplicateCheckRequest {
    @NotBlank(message = "체크할 조건은 필수입니다")
    @ApiModelProperty(value = "중복 체크할 자원 [name]")
    private String resource;

    @NotBlank(message = "체크할 조건의 값은 필수입니다")
    @ApiModelProperty(value = "중복 체크할 자원의 값")
    private String input;
}
