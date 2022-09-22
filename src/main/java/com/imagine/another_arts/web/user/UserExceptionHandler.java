package com.imagine.another_arts.web.user;

import com.imagine.another_arts.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.imagine.another_arts.web.user")
public class UserExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorDescription userNotFoundException(UserNotFoundException e) {
        return new ErrorDescription(
                false,
                "NOT_FOUND",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalUserModifyException.class)
    public ErrorDescription illegalUserModifyException(IllegalUserModifyException e) {
        return new ErrorDescription(
                false,
                "CONFLICT",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateUserInfoException.class)
    public ErrorDescription duplicateUserInfoException(DuplicateUserInfoException e) {
        return new ErrorDescription(
                false,
                "CONFLICT",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalUrlRequestException.class)
    public ErrorDescription illegalUrlRequestException(IllegalUrlRequestException e) {
        return new ErrorDescription(
                false,
                "BAD_REQUEST",
                e.getMessage()
        );
    }
}
