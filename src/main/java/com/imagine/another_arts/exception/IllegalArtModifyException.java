package com.imagine.another_arts.exception;

public class IllegalArtModifyException extends RuntimeException {
    public IllegalArtModifyException() {
        super();
    }

    public IllegalArtModifyException(String message) {
        super(message);
    }

    public IllegalArtModifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArtModifyException(Throwable cause) {
        super(cause);
    }

    protected IllegalArtModifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
