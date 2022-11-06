package com.imagine.another_arts.domain.art.service.dto.response;

import com.imagine.another_arts.common.CommonDateTranslator;
import com.imagine.another_arts.domain.art.repository.dto.response.BasicGeneralArt;
import lombok.Getter;

@Getter
public class BasicGeneralArtTranslator {
    private Long artId; // 작품 ID
    private String artName; // 작품명
    private String artDescription; // 작품 설명
    private Long artInitPrice; // 작품 초기 가격
    private String artRegisterDate; // 작품 등록 날짜
    private String artStorageName; // 작품 서버 저장명 (UUID)

    private Long artOwnerId; // 작품 주인 ID
    private String artOwnerNickname; // 작품 주인 닉네임
    private String artOwnerSchoolName; // 작품 주인 학교명

    public BasicGeneralArtTranslator(BasicGeneralArt generalArt) {
        this.artId = generalArt.getArtId();
        this.artName = generalArt.getArtName();
        this.artDescription = generalArt.getArtDescription();
        this.artInitPrice = generalArt.getArtInitPrice();
        this.artRegisterDate = CommonDateTranslator.translateLocalDateTimeToString(generalArt.getArtRegisterDate());
        this.artStorageName = generalArt.getArtStorageName();

        this.artOwnerId = generalArt.getArtOwnerId();
        this.artOwnerNickname = generalArt.getArtOwnerNickname();
        this.artOwnerSchoolName = generalArt.getArtOwnerSchoolName();
    }
}
