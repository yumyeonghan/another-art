package com.imagine.another_arts.web.login.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindIdResponse {
    private Long userId;
    private String loginId;
}