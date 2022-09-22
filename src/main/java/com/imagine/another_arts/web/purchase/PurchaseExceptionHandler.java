package com.imagine.another_arts.web.purchase;

import com.imagine.another_arts.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.imagine.another_arts.web.purchase")
public class PurchaseExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AuctionNotFoundException.class)
    public ErrorDescription auctionNotFoundException(AuctionNotFoundException e) {
        return new ErrorDescription(
                false,
                "NOT_FOUND",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ArtNotFoundException.class)
    public ErrorDescription artNotFoundException(ArtNotFoundException e) {
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
    @ExceptionHandler(NotClosedAuctionException.class)
    public ErrorDescription notClosedAuctionException(NotClosedAuctionException e) {
        return new ErrorDescription(
                false,
                "BAD_REQUEST",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PointNotEnoughException.class)
    public ErrorDescription pointNotEnoughtException(PointNotEnoughException e) {
        return new ErrorDescription(
                false,
                "BAD_REQUEST",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnQualifiedUserException.class)
    public ErrorDescription unQualifiedUserException(UnQualifiedUserException e) {
        return new ErrorDescription(
                false,
                "BAD_REQUEST",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ArtSoldOutException.class)
    public ErrorDescription artSoldOutException(ArtSoldOutException e) {
        return new ErrorDescription(
                false,
                "BAD_REQUEST",
                e.getMessage()
        );
    }
}