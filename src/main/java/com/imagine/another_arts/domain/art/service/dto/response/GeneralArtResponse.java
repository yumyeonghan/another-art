package com.imagine.another_arts.domain.art.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class GeneralArtResponse {
    private Long artLikeCount;
    private BasicGeneralArtResponse generalArt;
    private List<String> artHashtagList = new ArrayList<>();
}
