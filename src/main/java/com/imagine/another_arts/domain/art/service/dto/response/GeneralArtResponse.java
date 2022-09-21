package com.imagine.another_arts.domain.art.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GeneralArtResponse extends ArtResponse {
    private Long artLikeCount;
    private BasicGeneralArtResponse generalArt;
    private List<String> artHashtagList;
}
