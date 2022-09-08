package com.imagine.another_arts.web.point.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PointUseResponse {

    private Long id;
    private String loginId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

}
