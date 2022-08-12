package com.imagine.another_arts.domain.art.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.imagine.another_arts.domain.art.Art;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ArtInfoDto {
    private Long artId; // 작품 ID(PK)
    private String name; // 작품 이름
    private String description; // 작품 설명
    private Integer initPrice; // 초기 설정 가격
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerDate; // 작품 등록날짜
    private String artImage; // 서버상에 저장된 작품 이름
    private ArtUserDto artOwner; // 작품 주인
    private List<String> hashtagList = new ArrayList<>(); // 작품 해시태그 목록

    public ArtInfoDto(Art art, List<String> hashtag){
        this.artId = art.getId();
        this.name = art.getName();
        this.description = art.getDescription();
        this.initPrice = art.getInitPrice();
        this.registerDate = art.getRegisterDate();
        this.artImage = art.getStorageName();
        this.artOwner = new ArtUserDto(art.getUser());
        this.hashtagList.addAll(hashtag);
    }
}
