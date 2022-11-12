package com.imagine.another_arts.domain.art.repository.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BasicAuctionArt {
    private Long auctionId; // 경매 ID
    private Long highestBidUserId; // 최고 비드 유저 ID
    private String highestBidUserNickname; // 최고 비드 유저 닉네임
    private String highestBidUserSchoolName; // 최고 비드 유저 학교명
    private Long highestBidPrice; // 최고 비드 가격
    private LocalDateTime auctionStartDate; // 경매 시작 날짜
    private LocalDateTime auctionEndDate; // 경매 종료 날짜

    private Long artId; // 작품 ID
    private String artName; // 작품명
    private String artDescription; // 작품 설명
    private Long artInitPrice; // 작품 초기 가격
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime artRegisterDate; // 작품 등록 날짜
    private String artStorageName; // 작품 서버 저장명 (UUID)

    private Long artOwnerId; // 작품 주인 ID
    private String artOwnerNickname; // 작품 주인 닉네임
    private String artOwnerSchoolName; // 작품 주인 학교명

    @QueryProjection
    public BasicAuctionArt(Long auctionId, Long highestBidUserId, String highestBidUserNickname,
                           String highestBidUserSchoolName, Long highestBidPrice, LocalDateTime auctionStartDate,
                           LocalDateTime auctionEndDate, Long artId, String artName, String artDescription, Long artInitPrice,
                           LocalDateTime artRegisterDate, String artStorageName, Long artOwnerId, String artOwnerNickname, String artOwnerSchoolName) {
        this.auctionId = auctionId;
        this.highestBidUserId = highestBidUserId;
        this.highestBidUserNickname = highestBidUserNickname;
        this.highestBidUserSchoolName = highestBidUserSchoolName;
        this.highestBidPrice = highestBidPrice;
        this.auctionStartDate = auctionStartDate;
        this.auctionEndDate = auctionEndDate;
        this.artId = artId;
        this.artName = artName;
        this.artDescription = artDescription;
        this.artInitPrice = artInitPrice;
        this.artRegisterDate = artRegisterDate;
        this.artStorageName = artStorageName;
        this.artOwnerId = artOwnerId;
        this.artOwnerNickname = artOwnerNickname;
        this.artOwnerSchoolName = artOwnerSchoolName;
    }
}
