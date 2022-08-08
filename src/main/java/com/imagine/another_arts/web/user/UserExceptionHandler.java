package com.imagine.another_arts.web.user;

import com.imagine.another_arts.exception.ErrorDescription;
import com.imagine.another_arts.exception.NotExistUserIdException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(NotExistUserIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDescription notExistUserIdExHandler(NotExistUserIdException e) {

        return new ErrorDescription(false, "BAD_REQUEST", e.getMessage());
    }


}
