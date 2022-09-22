package com.imagine.another_arts.web.point.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PointUseResponse {
    private Long id;
    private String loginId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
}
