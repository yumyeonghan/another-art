package com.imagine.another_arts.exception;

public class BidAmountNotEnoughException extends RuntimeException {
    public BidAmountNotEnoughException() {
        super();
    }

    public BidAmountNotEnoughException(String message) {
        super(message);
    }

    public BidAmountNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public BidAmountNotEnoughException(Throwable cause) {
        super(cause);
    }

    protected BidAmountNotEnoughException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
