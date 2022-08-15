package com.imagine.another_arts.exception;

public class IllegalHashtagDeleteException extends RuntimeException {
    public IllegalHashtagDeleteException() {
        super();
    }

    public IllegalHashtagDeleteException(String message) {
        super(message);
    }

    public IllegalHashtagDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalHashtagDeleteException(Throwable cause) {
        super(cause);
    }

    protected IllegalHashtagDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
