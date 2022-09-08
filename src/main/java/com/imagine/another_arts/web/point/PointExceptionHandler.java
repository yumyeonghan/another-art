package com.imagine.another_arts.web.point;

import com.imagine.another_arts.exception.ErrorDescription;
import com.imagine.another_arts.exception.IllegalUserInfoFoundException;
import com.imagine.another_arts.exception.PointNotFullException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.imagine.another_arts.web.point")
public class PointExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(IllegalUserInfoFoundException.class)
    public ErrorDescription illegalUserInfoFoundException(IllegalUserInfoFoundException e) {
        return new ErrorDescription(
                false,
                "UNAUTHORIZED",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PointNotFullException.class)
    public ErrorDescription pointNotFullException(PointNotFullException e) {
        return new ErrorDescription(
                false,
                "BAD_REQUEST",
                e.getMessage()
        );
    }


}
