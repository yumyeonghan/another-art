package com.imagine.another_arts.exception;

public class PurchaserNotQualifiedException extends RuntimeException {
    public PurchaserNotQualifiedException() {
        super();
    }

    public PurchaserNotQualifiedException(String message) {
        super(message);
    }

    public PurchaserNotQualifiedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PurchaserNotQualifiedException(Throwable cause) {
        super(cause);
    }

    protected PurchaserNotQualifiedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
