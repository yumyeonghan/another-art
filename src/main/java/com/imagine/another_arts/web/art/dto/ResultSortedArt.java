package com.imagine.another_arts.web.art.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultSortedArt<T> {
    private boolean successResponse; // 응답 성공 여부
    private int count; // result size
    private T artList; // result
}
