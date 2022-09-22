package com.imagine.another_arts.domain.point.enums;

public enum PointType {
    JOIN, // 신규 가입
    CHARGE, // 포인트 충전
    REFUND, // 포인트 환불
    USE, // 포인트 사용 (작품 구매)
    SOLD // 작품 낙찰 금액 적용 (작품 판매자 대상 enum)
    ;
}
