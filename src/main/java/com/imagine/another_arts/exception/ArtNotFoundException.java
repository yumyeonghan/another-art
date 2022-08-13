package com.imagine.another_arts.exception;

public class ArtNotFoundException extends RuntimeException {
    public ArtNotFoundException() {
        super();
    }

    public ArtNotFoundException(String message) {
        super(message);
    }

    public ArtNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArtNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ArtNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
