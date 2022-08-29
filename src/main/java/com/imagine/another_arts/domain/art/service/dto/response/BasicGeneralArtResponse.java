package com.imagine.another_arts.domain.art.service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.imagine.another_arts.domain.art.Art;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class BasicGeneralArtResponse {
    private BigInteger artId; // 작품 ID
    private String artName; // 작품명
    private String artDescription; // 작품 설명
    private Integer artInitPrice; // 작품 초기 가격
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp artRegisterDate; // 작품 등록 날짜
    private String artStorageName; // 작품 서버 저장명 (UUID)

    private BigInteger artOwnerId; // 작품 주인 ID
    private String artOwnerNickname; // 작품 주인 닉네임
    private String artOwnerSchoolName; // 작품 주인 학교명

    public BasicGeneralArtResponse(Art art) {
        this.artId = BigInteger.valueOf(art.getId());
        this.artName = art.getName();
        this.artDescription = art.getDescription();
        this.artInitPrice = art.getInitPrice();
        this.artRegisterDate = Timestamp.valueOf(art.getRegisterDate());
        this.artStorageName = art.getStorageName();

        this.artOwnerId = BigInteger.valueOf(art.getUser().getId());
        this.artOwnerNickname = art.getUser().getNickname();
        this.artOwnerSchoolName = art.getUser().getSchoolName();
    }
}
