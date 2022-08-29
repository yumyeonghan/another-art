package com.imagine.another_arts.web.login.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Long userId;
    private String userName;
    private String userNickname;
    private String loginId;
}
