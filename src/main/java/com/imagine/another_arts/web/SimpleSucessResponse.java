package com.imagine.another_arts.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleSucessResponse { // 간단한 true 응답만을 보내기 위한 클래스
    private boolean successResponse;
}
