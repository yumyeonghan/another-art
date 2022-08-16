package com.imagine.another_arts.web.art.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
public class HashtagListDto { // 해시태그 List를 DTO로 받기 위한 클래스
    @ApiParam(value = "추가할 해시태그 목록", required = true)
    private List<@NotBlank String> hashtagList;
}
