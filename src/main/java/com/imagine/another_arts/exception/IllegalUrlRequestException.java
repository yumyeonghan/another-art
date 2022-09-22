package com.imagine.another_arts.exception;

public class IllegalUrlRequestException extends RuntimeException {
    public IllegalUrlRequestException() {
        super();
    }

    public IllegalUrlRequestException(String message) {
        super(message);
    }

    public IllegalUrlRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalUrlRequestException(Throwable cause) {
        super(cause);
    }

    protected IllegalUrlRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
