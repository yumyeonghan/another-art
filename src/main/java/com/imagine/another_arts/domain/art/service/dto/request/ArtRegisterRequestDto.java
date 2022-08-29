package com.imagine.another_arts.domain.art.service.dto.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArtRegisterRequestDto {
    private String saleType;
    private Long userId;
    private String name;
    private String description;
    private Integer initPrice;
    private MultipartFile file;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
    private List<String> hashtagList;

    // 경매 작품 등록
    public ArtRegisterRequestDto(String saleType, Long userId, String name, String description, Integer initPrice, MultipartFile file,
                                 LocalDateTime startDate, LocalDateTime endDate, List<String> hashtagList) {
        this.saleType = saleType;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.initPrice = initPrice;
        this.file = file;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hashtagList = hashtagList;
    }

    // 일반 작품 등록
    public ArtRegisterRequestDto(String saleType, Long userId, String name, String description, Integer initPrice, MultipartFile file, List<String> hashtagList) {
        this.saleType = saleType;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.initPrice = initPrice;
        this.file = file;
        this.hashtagList = hashtagList;
    }
}
