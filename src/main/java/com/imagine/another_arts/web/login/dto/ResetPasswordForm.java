package com.imagine.another_arts.web.login.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class ResetPasswordForm {
    @NotBlank(message = "아이디는 필수입니다")
    @ApiParam(value = "로그인 아이디", required = true)
    private String loginId;

    @NotBlank(message = "변경할 비밀번호는 필수입니다")
    @ApiParam(value = "변경할 비밀번호", required = true)
    private String changePassword;
}
