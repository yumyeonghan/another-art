package com.imagine.another_arts.domain.art.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.arthashtag.ArtHashtag;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SortedArtDto {
    private Long artId; // 작품 ID(PK)
    private String name; // 작품 이름
    private String description; // 작품 설명
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerDate; // 작품 등록날짜
    private String artImage; // 서버상에 저장된 작품 이름
    private SortedArtUserDto artOwner; // 작품 주인
    private List<SortedArtHashtagDto> artHashList = new ArrayList<>(); // 작품 해시태그 목록

    public SortedArtDto(Art art, List<ArtHashtag> artHashtagList){
        this.artId = art.getId();
        this.name = art.getName();
        this.description = art.getDescription();
        this.registerDate = art.getRegisterDate();
        this.artImage = art.getStorageName();
        this.artOwner = new SortedArtUserDto(art.getUser());
        for (ArtHashtag artHashtag : artHashtagList) {
            this.artHashList.add(new SortedArtHashtagDto(artHashtag));
        }
    }
}
