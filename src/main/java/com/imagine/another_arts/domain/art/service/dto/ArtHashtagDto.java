package com.imagine.another_arts.domain.art.service.dto;

import com.imagine.another_arts.domain.arthashtag.ArtHashtag;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtHashtagDto {
    private String name;

    public ArtHashtagDto(ArtHashtag artHashtag){
        this.name = artHashtag.getHashtag().getName();
    }
}
