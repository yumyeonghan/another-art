package com.imagine.another_arts.web.art;

import com.imagine.another_arts.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.imagine.another_arts.web.art")
public class ArtExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ArtNotFoundException.class)
    public ErrorDescription artNotFoundException(ArtNotFoundException e){
        return new ErrorDescription(
                false,
                "NOT_FOUND",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorDescription userNotFoundException(UserNotFoundException e) {
        return new ErrorDescription(
                false,
                "NOT_FOUND",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArtFileUploadException.class)
    public ErrorDescription illegalArtFileUploadException(IllegalArtFileUploadException e) {
        return new ErrorDescription(
                false,
                "BAD_REQUEST",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RunTimeArtRegisterException.class)
    public ErrorDescription runTimeArtRegisterException(RunTimeArtRegisterException e) {
        return new ErrorDescription(
                false,
                "INTERNAL_SERVER_ERROR",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AuctionNotFoundException.class)
    public ErrorDescription auctionNotFoundException(AuctionNotFoundException e) {
        return new ErrorDescription(
                false,
                "NOT_FOUND",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalHashtagDeleteException.class)
    public ErrorDescription illegalHashtagDeleteException(IllegalHashtagDeleteException e) {
        return new ErrorDescription(
                false,
                "BAD_REQUEST",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArtModifyException.class)
    public ErrorDescription illegalArtEditException(IllegalArtModifyException e) {
        return new ErrorDescription(
                false,
                "BAD_REQUEST",
                e.getMessage()
        );
    }
}
