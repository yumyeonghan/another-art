package com.imagine.another_arts.exception;

public class UserDuplicationException extends RuntimeException {
    public UserDuplicationException() {
    }

    public UserDuplicationException(String s) {
        super(s);
    }

    public UserDuplicationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public UserDuplicationException(Throwable throwable) {
        super(throwable);
    }

    public UserDuplicationException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
