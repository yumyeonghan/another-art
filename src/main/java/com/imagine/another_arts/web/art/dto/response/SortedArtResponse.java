package com.imagine.another_arts.web.art.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SortedArtResponse<T> {
    private int count; // result size
    private T artList; // result
}
