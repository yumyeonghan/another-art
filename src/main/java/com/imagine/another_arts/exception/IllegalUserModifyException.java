package com.imagine.another_arts.exception;

public class IllegalUserModifyException extends RuntimeException {
    public IllegalUserModifyException() {
        super();
    }

    public IllegalUserModifyException(String message) {
        super(message);
    }

    public IllegalUserModifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalUserModifyException(Throwable cause) {
        super(cause);
    }

    protected IllegalUserModifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
