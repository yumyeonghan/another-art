package com.imagine.another_arts.web.login.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "아이디는 필수입니다")
    @ApiParam(value = "로그인 아이디", required = true)
    private String loginId;

    @NotBlank(message = "비밀번호는 필수입니다")
    @ApiParam(value = "로그인 비밀번호", required = true)
    private String loginPassword;
}
