package com.imagine.another_arts.exception;

public class DuplicationArtNameException extends RuntimeException {
    public DuplicationArtNameException() {
        super();
    }

    public DuplicationArtNameException(String message) {
        super(message);
    }

    public DuplicationArtNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicationArtNameException(Throwable cause) {
        super(cause);
    }

    protected DuplicationArtNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
