package com.imagine.another_arts.exception;

public class PointNotEnoughException extends RuntimeException {
    public PointNotEnoughException() {
        super();
    }

    public PointNotEnoughException(String message) {
        super(message);
    }

    public PointNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public PointNotEnoughException(Throwable cause) {
        super(cause);
    }

    protected PointNotEnoughException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
