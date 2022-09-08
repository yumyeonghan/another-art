package com.imagine.another_arts.exception;

public class PointNotFullException extends RuntimeException {
    public PointNotFullException() {
        super();
    }

    public PointNotFullException(String message) {
        super(message);
    }

    public PointNotFullException(String message, Throwable cause) {
        super(message, cause);
    }

    public PointNotFullException(Throwable cause) {
        super(cause);
    }

    protected PointNotFullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
