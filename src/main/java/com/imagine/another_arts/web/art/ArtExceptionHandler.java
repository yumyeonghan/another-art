package com.imagine.another_arts.web.art;

import com.imagine.another_arts.exception.ArtNotFoundException;
import com.imagine.another_arts.exception.ErrorDescription;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.imagine.another_arts.web.art")
public class ArtExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ArtNotFoundException.class)
    public ErrorDescription artNotFoundException(ArtNotFoundException e){
        return new ErrorDescription(
                false,
                "BAD-REQUEST",
                e.getMessage()
        );
    }
}
