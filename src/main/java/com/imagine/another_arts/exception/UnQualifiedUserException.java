package com.imagine.another_arts.exception;

public class UnQualifiedUserException extends RuntimeException {
    public UnQualifiedUserException() {
        super();
    }

    public UnQualifiedUserException(String message) {
        super(message);
    }

    public UnQualifiedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnQualifiedUserException(Throwable cause) {
        super(cause);
    }

    protected UnQualifiedUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
