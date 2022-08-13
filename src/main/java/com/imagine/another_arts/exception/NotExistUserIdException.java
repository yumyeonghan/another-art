package com.imagine.another_arts.exception;

public class NotExistUserIdException extends RuntimeException {
    public NotExistUserIdException(String s) {
        super(s);
    }
}
