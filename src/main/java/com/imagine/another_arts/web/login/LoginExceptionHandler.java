package com.imagine.another_arts.web.login;

import com.imagine.another_arts.exception.ErrorDescription;
import com.imagine.another_arts.exception.IllegalUserInfoFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.imagine.another_arts.web.login")
public class LoginExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalUserInfoFoundException.class)
    public ErrorDescription illegalUserInfoFoundException(IllegalUserInfoFoundException e) {
        return new ErrorDescription(
                false,
                "BAD_REQUEST",
                e.getMessage()
        );
    }
}
