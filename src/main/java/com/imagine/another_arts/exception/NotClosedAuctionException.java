package com.imagine.another_arts.exception;

public class NotClosedAuctionException extends RuntimeException {
    public NotClosedAuctionException() {
        super();
    }

    public NotClosedAuctionException(String message) {
        super(message);
    }

    public NotClosedAuctionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotClosedAuctionException(Throwable cause) {
        super(cause);
    }

    protected NotClosedAuctionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
