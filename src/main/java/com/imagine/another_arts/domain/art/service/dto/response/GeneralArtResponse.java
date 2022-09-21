package com.imagine.another_arts.domain.art.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralArtResponse extends ArtResponse {
    private Long artLikeCount;
    private BasicGeneralArtResponse generalArt;
    private List<String> artHashtagList;
}
