package com.imagine.another_arts.web.art.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SingleArtResponse<T> {
    private T art; // result
}
