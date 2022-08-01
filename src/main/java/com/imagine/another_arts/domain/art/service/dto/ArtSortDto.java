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
public class ArtSortDto {
    private Long artId;
    private String name;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerDate;
    private String artImage;
    private UserSortDto artOwner;
    private List<ArtHashtagDto> artHashList = new ArrayList<>();

    public ArtSortDto(Art art, List<ArtHashtag> artHashtagList){
        this.artId = art.getId();
        this.name = art.getName();
        this.description = art.getDescription();
        this.registerDate = art.getRegisterDate();
        this.artImage = art.getStorageName();
        this.artOwner = new UserSortDto(art.getUser());
        for (ArtHashtag artHashtag : artHashtagList) {
            this.artHashList.add(new ArtHashtagDto(artHashtag));
        }
    }
}
