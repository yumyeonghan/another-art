package com.imagine.another_arts.web.art.dto.response;

import com.imagine.another_arts.web.art.dto.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SortedArtResponse<T> {
    private int contentSize; // content size
    private T artList; // result
    private Pagination pagination; // pagination information
}
