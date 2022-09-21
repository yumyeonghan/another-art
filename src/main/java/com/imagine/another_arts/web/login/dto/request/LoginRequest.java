package com.imagine.another_arts.web.login.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "아이디는 필수입니다")
    @ApiModelProperty(value = "로그인 아이디", required = true)
    private String loginId;

    @NotBlank(message = "비밀번호는 필수입니다")
    @ApiModelProperty(value = "로그인 비밀번호", required = true)
    private String loginPassword;
}
