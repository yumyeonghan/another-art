package com.imagine.another_arts.web.point.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PointUseRequest {
    private String loginId;
    private Integer amount;
}
