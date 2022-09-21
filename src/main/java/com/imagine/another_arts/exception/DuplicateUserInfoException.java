package com.imagine.another_arts.exception;

public class DuplicateUserInfoException extends RuntimeException {
    public DuplicateUserInfoException() {
        super();
    }

    public DuplicateUserInfoException(String message) {
        super(message);
    }

    public DuplicateUserInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateUserInfoException(Throwable cause) {
        super(cause);
    }

    protected DuplicateUserInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
