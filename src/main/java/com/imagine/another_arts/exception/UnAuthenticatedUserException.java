package com.imagine.another_arts.exception;

public class UnAuthenticatedUserException extends RuntimeException {
    public UnAuthenticatedUserException() {
        super();
    }

    public UnAuthenticatedUserException(String message) {
        super(message);
    }

    public UnAuthenticatedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthenticatedUserException(Throwable cause) {
        super(cause);
    }

    protected UnAuthenticatedUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
