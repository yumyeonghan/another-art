package com.imagine.another_arts.web.point;

import com.imagine.another_arts.exception.ErrorDescription;
import com.imagine.another_arts.exception.PointNotEnoughException;
import com.imagine.another_arts.exception.UnAuthenticatedUserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.imagine.another_arts.web.point")
public class PointExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthenticatedUserException.class)
    public ErrorDescription unAuthenticatedUserException(UnAuthenticatedUserException e) {
        return new ErrorDescription(
                false,
                "UNAUTHORIZED",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PointNotEnoughException.class)
    public ErrorDescription pointNotFullException(PointNotEnoughException e) {
        return new ErrorDescription(
                false,
                "BAD_REQUEST",
                e.getMessage()
        );
    }
}
