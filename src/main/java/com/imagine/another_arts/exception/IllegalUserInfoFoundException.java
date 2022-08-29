package com.imagine.another_arts.exception;

public class IllegalUserInfoFoundException extends RuntimeException {
    public IllegalUserInfoFoundException() {
        super();
    }

    public IllegalUserInfoFoundException(String message) {
        super(message);
    }

    public IllegalUserInfoFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalUserInfoFoundException(Throwable cause) {
        super(cause);
    }

    protected IllegalUserInfoFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
