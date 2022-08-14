package com.imagine.another_arts.web.login;

import com.imagine.another_arts.exception.ErrorDescription;
import com.imagine.another_arts.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.imagine.another_arts.web.login")
public class LoginExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorDescription userNotFoundException(UserNotFoundException e){
        return new ErrorDescription(
                false,
                "BAD-REQUEST",
                e.getMessage()
        );
    }
}
