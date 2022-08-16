package com.imagine.another_arts.exception;

public class IllegalArtFileUploadException extends RuntimeException {
    public IllegalArtFileUploadException() {
        super();
    }

    public IllegalArtFileUploadException(String message) {
        super(message);
    }

    public IllegalArtFileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArtFileUploadException(Throwable cause) {
        super(cause);
    }

    protected IllegalArtFileUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
