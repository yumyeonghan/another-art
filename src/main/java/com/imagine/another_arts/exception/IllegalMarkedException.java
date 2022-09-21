package com.imagine.another_arts.exception;

public class IllegalMarkedException extends RuntimeException {
    public IllegalMarkedException() {
        super();
    }

    public IllegalMarkedException(String message) {
        super(message);
    }

    public IllegalMarkedException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalMarkedException(Throwable cause) {
        super(cause);
    }

    protected IllegalMarkedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
