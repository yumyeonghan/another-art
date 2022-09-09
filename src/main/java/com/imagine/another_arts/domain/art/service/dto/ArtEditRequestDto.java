package com.imagine.another_arts.domain.art.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtEditRequestDto {
    private String name;
    private String description;
}
