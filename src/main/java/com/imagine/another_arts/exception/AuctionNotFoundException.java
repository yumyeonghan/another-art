package com.imagine.another_arts.exception;

public class AuctionNotFoundException extends RuntimeException {
    public AuctionNotFoundException() {
        super();
    }

    public AuctionNotFoundException(String message) {
        super(message);
    }

    public AuctionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuctionNotFoundException(Throwable cause) {
        super(cause);
    }

    protected AuctionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
