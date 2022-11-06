package com.imagine.another_arts.domain.art.service.dto.response;

import com.imagine.another_arts.common.CommonDateTranslator;
import com.imagine.another_arts.domain.art.repository.dto.response.BasicAuctionArt;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicAuctionArtTranslator {
    private Long auctionId; // 경매 ID
    private Long highestBidUserId; // 최고 비드 유저 ID
    private String highestBidUserNickname; // 최고 비드 유저 닉네임
    private String highestBidUserSchoolName; // 최고 비드 유저 학교명
    private Long highestBidPrice; // 최고 비드 가격
    private String auctionStartDate; // 경매 시작 날짜
    private String auctionEndDate; // 경매 종료 날짜

    private Long artId; // 작품 ID
    private String artName; // 작품명
    private String artDescription; // 작품 설명
    private Long artInitPrice; // 작품 초기 가격
    private String artRegisterDate; // 작품 등록 날짜
    private String artStorageName; // 작품 서버 저장명 (UUID)

    private Long artOwnerId; // 작품 주인 ID
    private String artOwnerNickname; // 작품 주인 닉네임
    private String artOwnerSchoolName; // 작품 주인 학교명

    public BasicAuctionArtTranslator(BasicAuctionArt auctionArt) {
        this.auctionId = auctionArt.getAuctionId();
        this.highestBidUserId = auctionArt.getHighestBidUserId();
        this.highestBidUserNickname = auctionArt.getHighestBidUserNickname();
        this.highestBidUserSchoolName = auctionArt.getHighestBidUserSchoolName();
        this.highestBidPrice = auctionArt.getHighestBidPrice();
        this.auctionStartDate = CommonDateTranslator.translateLocalDateTimeToString(auctionArt.getAuctionStartDate());
        this.auctionEndDate = CommonDateTranslator.translateLocalDateTimeToString(auctionArt.getAuctionEndDate());

        this.artId = auctionArt.getArtId();
        this.artName = auctionArt.getArtName();
        this.artDescription = auctionArt.getArtDescription();
        this.artInitPrice = auctionArt.getArtInitPrice();
        this.artRegisterDate = CommonDateTranslator.translateLocalDateTimeToString(auctionArt.getArtRegisterDate());
        this.artStorageName = auctionArt.getArtStorageName();

        this.artOwnerId = auctionArt.getArtOwnerId();
        this.artOwnerNickname = auctionArt.getArtOwnerNickname();
        this.artOwnerSchoolName = auctionArt.getArtOwnerSchoolName();
    }
}
