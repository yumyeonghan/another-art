package com.imagine.another_arts.exception;

public class ClosedAuctionException extends RuntimeException{
    public ClosedAuctionException() {
        super();
    }

    public ClosedAuctionException(String message) {
        super(message);
    }

    public ClosedAuctionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClosedAuctionException(Throwable cause) {
        super(cause);
    }

    protected ClosedAuctionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
