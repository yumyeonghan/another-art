package com.imagine.another_arts.web.login.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindPasswordRequest {
    @NotBlank(message = "아이디는 필수입니다")
    @ApiModelProperty(value = "로그인 아이디", required = true)
    private String loginId;

    @NotBlank(message = "이름은 필수입니다")
    @ApiModelProperty(value = "사용자 이름", required = true)
    private String name;

    @NotBlank(message = "이메일은 필수입니다")
    @ApiModelProperty(value = "사용자 이메일", required = true)
    private String email;
}
