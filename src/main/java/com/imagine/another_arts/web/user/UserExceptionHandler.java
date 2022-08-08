package com.imagine.another_arts.web.user;

import com.imagine.another_arts.web.user.exception.NotExistUserIdException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(NotExistUserIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult notExistUserIdExHandler(NotExistUserIdException e) {
        return new ErrorResult("USER_EX", e.getMessage());
    }

    @Data
    @AllArgsConstructor
    static class ErrorResult {
        String code;
        String message;
    }
}
