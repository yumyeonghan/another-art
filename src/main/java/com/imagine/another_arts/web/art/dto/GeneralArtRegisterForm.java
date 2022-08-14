package com.imagine.another_arts.web.art.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
public class GeneralArtRegisterForm {
    private Long userId;

    private String name;

    private String description;

    private Integer initPrice;

    private MultipartFile file;

    private List<String> hashtagList;
}
