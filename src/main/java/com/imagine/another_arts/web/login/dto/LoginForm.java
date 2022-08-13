package com.imagine.another_arts.web.login.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String loginPassword;
}
