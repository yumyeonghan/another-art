package com.imagine.another_arts.web.login.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindIdResponse {
    private Long userId;
    private String loginId;
}
