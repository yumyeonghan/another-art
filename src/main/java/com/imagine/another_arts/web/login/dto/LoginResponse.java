package com.imagine.another_arts.web.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private boolean successResponse;
    private String name;
    private String loginId;
    private String loginPassword;
}
