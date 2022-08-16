package com.imagine.another_arts.web.art.dto;

import com.imagine.another_arts.domain.art.service.dto.GeneralArtRegisterRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
public class GeneralArtRegisterRequest {
    private Long userId;
    private String name;
    private String description;
    private Integer initPrice;
    private MultipartFile file;
    private List<String> hashtagList;

    public GeneralArtRegisterRequestDto toServiceDto() {
        return new GeneralArtRegisterRequestDto(
                this.userId,
                this.name,
                this.description,
                this.initPrice,
                this.file,
                this.hashtagList
        );
    }
}
