package com.imagine.another_arts.web.art.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HashtagDeleteRequest {
    @ApiModelProperty(value = "삭제할 해시태그 리스트", required = true)
    private List<@NotBlank String> hashtagList;
}
