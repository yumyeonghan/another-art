package com.imagine.another_arts.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AnotherArtErrorCode {
    // User
    AUTHENTICATION_USER(HttpStatus.UNAUTHORIZED, "로그인하지 않은 사용자입니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 정보가 존재하지 않습니다"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    DUPLICATE_USER_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다"),
    DUPLICATE_USER_LOGIN_ID(HttpStatus.CONFLICT, "중복된 로그인 아이디입니다"),
    DUPLICATE_USER_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임입니다"),
    DUPLICATE_USER_PHONENUMBER(HttpStatus.CONFLICT, "중복된 전화번호입니다"),
    ILLEGAL_USER_API_REQUEST(HttpStatus.BAD_REQUEST, "타인의 정보는 요청할 수 없습니다"),
    INELIGIBLE_USER_PURCHASE(HttpStatus.BAD_REQUEST, "구매 자격이 없는 사용자입니다"),

    // Art
    ART_NOT_FOUND(HttpStatus.NOT_FOUND, "작품 정보가 존재하지 않습니다"),
    ART_SOLD_OUT(HttpStatus.BAD_REQUEST, "이미 판매 완료된 작품입니다"),
    DUPLICATE_ART_NAME(HttpStatus.CONFLICT, "중복된 작품명입니다"),
    ALREADY_AUCTION_PROCESS(HttpStatus.CONFLICT, "입찰이 이미 진행된 작품입니다"),
    ILLEGAL_ART_UPLOAD(HttpStatus.BAD_REQUEST, "작품 파일이 업로드되지 않았습니다"),
    ALREADY_LIKE_MARKING(HttpStatus.BAD_REQUEST, "이미 찜한 작품입니다"),
    ILLEGAL_LIKE_MARKING(HttpStatus.BAD_REQUEST, "자신의 작품을 찜할 수 없습니다"),
    ILLEGAL_LIKE_MARKING_CANCEL(HttpStatus.BAD_REQUEST, "이미 찜을 취소하였거나 찜을 한 적이 없는 작품입니다"),

    // Auction
    AUCTION_NOT_FOUND(HttpStatus.NOT_FOUND, "경매 정보가 존재하지 않습니다"),
    BID_AMOUNT_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "입찰 금액이 부족합니다"),
    CLOSED_AUCTION(HttpStatus.BAD_REQUEST, "이미 종료된 경매입니다"),
    NOT_CLOSED_AUCTION(HttpStatus.BAD_REQUEST, "종료되지 않은 경매입니다"),

    // Point
    POINT_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "포인트가 부족합니다"),

    // API Request
    ILLEGAL_URL_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다"),

    // RunTime Server Error
    RUN_TIME_ART_REGISTER(HttpStatus.INTERNAL_SERVER_ERROR, "작품 등록간 서버상에 오류가 발생했습니다")

    ;

    private final HttpStatus status;
    private final String message;

    AnotherArtErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}