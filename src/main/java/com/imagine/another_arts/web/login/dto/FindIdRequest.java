package com.imagine.another_arts.web.login.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class FindIdRequest {
    @NotEmpty(message = "이름은 필수입니다")
    @ApiParam(value = "사용자 이름", required = true)
    private String name;

    @NotEmpty(message = "이메일은 필수입니다")
    @ApiParam(value = "사용자 이메일", required = true)
    private String email;
}
