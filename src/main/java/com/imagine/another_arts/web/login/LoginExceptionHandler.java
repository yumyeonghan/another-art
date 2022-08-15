package com.imagine.another_arts.web.login;

import com.imagine.another_arts.exception.ErrorDescription;
import com.imagine.another_arts.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.imagine.another_arts.web.login")
public class LoginExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorDescription userNotFoundException(UserNotFoundException e){
        return new ErrorDescription(
                false,
                "NOT_FOUND",
                e.getMessage()
        );
    }
}
