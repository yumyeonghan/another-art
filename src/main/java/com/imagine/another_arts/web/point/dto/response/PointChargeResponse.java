package com.imagine.another_arts.web.point.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PointChargeResponse {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

}
