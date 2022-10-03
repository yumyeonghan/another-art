package com.imagine.another_arts.web;

import com.imagine.another_arts.exception.AnotherArtErrorCode;
import com.imagine.another_arts.exception.AnotherArtException;
import com.imagine.another_arts.exception.ErrorResponse;
import com.imagine.another_arts.web.art.ArtController;
import com.imagine.another_arts.web.auction.AuctionController;
import com.imagine.another_arts.web.login.LoginController;
import com.imagine.another_arts.web.point.PointController;
import com.imagine.another_arts.web.purchase.PurchaseController;
import com.imagine.another_arts.web.user.UserController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {
        ArtController.class, AuctionController.class, LoginController.class,
        PointController.class, PurchaseController.class, UserController.class
})
public class ApiExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> catchAnotherArtException(AnotherArtException ex) {
        AnotherArtErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode));
    }
}
