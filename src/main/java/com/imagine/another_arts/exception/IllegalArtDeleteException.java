package com.imagine.another_arts.exception;

public class IllegalArtDeleteException extends RuntimeException {
    public IllegalArtDeleteException() {
        super();
    }

    public IllegalArtDeleteException(String message) {
        super(message);
    }

    public IllegalArtDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArtDeleteException(Throwable cause) {
        super(cause);
    }

    protected IllegalArtDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
