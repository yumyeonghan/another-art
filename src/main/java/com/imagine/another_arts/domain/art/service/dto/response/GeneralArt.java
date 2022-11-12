package com.imagine.another_arts.domain.art.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GeneralArt extends AbstractArt {
    private Long artLikeCount;
    private BasicGeneralArtTranslator generalArt;
    private List<String> artHashtagList;
}
