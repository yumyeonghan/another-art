package com.imagine.another_arts.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// JSON 예외 처리 클래스
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ErrorDescription {
    private boolean successResponse; // 응답 성공 여부
    private String statusCode; // Status Code
    private String description; // 예외에 대한 설명
}
