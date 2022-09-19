package com.imagine.another_arts.exception;

public class ArtSoldOutException extends RuntimeException {
    public ArtSoldOutException() {
        super();
    }

    public ArtSoldOutException(String message) {
        super(message);
    }

    public ArtSoldOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArtSoldOutException(Throwable cause) {
        super(cause);
    }

    protected ArtSoldOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
