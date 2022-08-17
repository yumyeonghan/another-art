package com.imagine.another_arts.exception;

public class IllegalUrlException extends RuntimeException {
    public IllegalUrlException() {
    }

    public IllegalUrlException(String s) {
        super(s);
    }

    public IllegalUrlException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public IllegalUrlException(Throwable throwable) {
        super(throwable);
    }

    public IllegalUrlException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
