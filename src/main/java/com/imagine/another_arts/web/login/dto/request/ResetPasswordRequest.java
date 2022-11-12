package com.imagine.another_arts.web.login.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "아이디는 필수입니다")
    @ApiModelProperty(value = "로그인 아이디", example = "user1", required = true)
    private String loginId;

    @NotBlank(message = "변경할 비밀번호는 필수입니다")
    @ApiModelProperty(value = "변경할 비밀번호", example = "1234", required = true)
    private String changePassword;
}
