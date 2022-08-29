package com.imagine.another_arts.domain.art.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtEditRequestDto {
    private String updateName;
    private String updateDescription;
}
