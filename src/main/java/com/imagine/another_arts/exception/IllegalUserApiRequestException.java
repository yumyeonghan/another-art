package com.imagine.another_arts.exception;

public class IllegalUserApiRequestException extends RuntimeException {
    public IllegalUserApiRequestException() {
        super();
    }

    public IllegalUserApiRequestException(String message) {
        super(message);
    }

    public IllegalUserApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalUserApiRequestException(Throwable cause) {
        super(cause);
    }

    protected IllegalUserApiRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
