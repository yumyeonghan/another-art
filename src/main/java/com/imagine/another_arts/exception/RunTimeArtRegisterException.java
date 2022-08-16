package com.imagine.another_arts.exception;

public class RunTimeArtRegisterException extends RuntimeException {
    public RunTimeArtRegisterException() {
        super();
    }

    public RunTimeArtRegisterException(String message) {
        super(message);
    }

    public RunTimeArtRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public RunTimeArtRegisterException(Throwable cause) {
        super(cause);
    }

    protected RunTimeArtRegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
