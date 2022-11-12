package com.imagine.another_arts.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 예외 응답 API 양식
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private int status; // 상태 코드 [401, 404, ..]
    private String code; // 상태 코드 설명 [BAD_REQUEST, NOT_FOUND, ..]
    private String message;

    private ErrorResponse(AnotherArtErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.code = errorCode.getStatus().getReasonPhrase();
        this.message = errorCode.getMessage();
    }

    public static ErrorResponse of(AnotherArtErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }
}
