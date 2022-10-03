package com.imagine.another_arts.exception;

import lombok.Getter;

@Getter
public class AnotherArtException extends RuntimeException {
    private AnotherArtErrorCode errorCode;

    private AnotherArtException(AnotherArtErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public static AnotherArtException type(AnotherArtErrorCode errorCode) {
        return new AnotherArtException(errorCode);
    }
}
