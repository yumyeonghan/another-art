package com.imagine.another_arts.domain.art.service.dto;

import com.imagine.another_arts.domain.arthashtag.ArtHashtag;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SortedArtHashtagDto {
    private String name;

    public SortedArtHashtagDto(ArtHashtag artHashtag){
        this.name = artHashtag.getHashtag().getName();
    }
}
