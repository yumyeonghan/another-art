package com.imagine.another_arts.web.login.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FindPasswordRequest {
    @NotEmpty(message = "아이디는 필수입니다")
    @ApiParam(value = "로그인 아이디", required = true)
    private String loginId;

    @NotEmpty(message = "이름은 필수입니다")
    @ApiParam(value = "사용자 이름", required = true)
    private String name;

    @NotEmpty(message = "이메일은 필수입니다")
    @ApiParam(value = "사용자 이메일", required = true)
    private String email;
}
