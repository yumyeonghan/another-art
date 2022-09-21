package com.imagine.another_arts.web.art;

import com.imagine.another_arts.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.imagine.another_arts.web.art")
public class ArtExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalMarkedException.class)
    public ErrorDescription illegalMarkedException(IllegalMarkedException e) {
        return new ErrorDescription(
                false,
                "BAD_REQUEST",
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ArtNotFoundException.class)
    public ErrorDescription artNotFoundException(ArtNotFoundException e){
        return new ErrorDescription(
                false,
                "NOT_FOUND",
                e.getMessage()
        );
    }

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

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalArtModifyException.class)
    public ErrorDescription illegalArtEditException(IllegalArtModifyException e) {
        return new ErrorDescription(
                false,
                "CONFLICT",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalArtDeleteException.class)
    public ErrorDescription illegalArtDeleteException(IllegalArtDeleteException e) {
        return new ErrorDescription(
                false,
                "CONFLICT",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateArtNameException.class)
    public ErrorDescription duplicationArtNameException(DuplicateArtNameException e) {
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
