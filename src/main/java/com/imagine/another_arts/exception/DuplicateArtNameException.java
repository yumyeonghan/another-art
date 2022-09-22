package com.imagine.another_arts.exception;

public class DuplicateArtNameException extends RuntimeException {
    public DuplicateArtNameException() {
        super();
    }

    public DuplicateArtNameException(String message) {
        super(message);
    }

    public DuplicateArtNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateArtNameException(Throwable cause) {
        super(cause);
    }

    protected DuplicateArtNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
