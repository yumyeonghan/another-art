package com.imagine.another_arts.web.auction;

import com.imagine.another_arts.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.imagine.another_arts.web.auction")
public class AuctionExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PointNotFullException.class)
    public ErrorDescription pointNotFullException(PointNotFullException e) {
        return new ErrorDescription(
                false,
                "BAD_REQUEST",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BidAmountNotEnoughException.class)
    public ErrorDescription bidAmountNotFullException(BidAmountNotEnoughException e) {
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
    @ExceptionHandler(AuctionNotFoundException.class)
    public ErrorDescription auctionNotFoundException(AuctionNotFoundException e) {
        return new ErrorDescription(
                false,
                "NOT_FOUND",
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClosedAuctionException.class)
    public ErrorDescription closedAuctionException(ClosedAuctionException e) {
        return new ErrorDescription(
                false,
                "NOT_FOUND",
                e.getMessage()
        );
    }
}