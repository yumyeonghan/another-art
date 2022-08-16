package com.imagine.another_arts.web.art.dto;

import com.imagine.another_arts.domain.art.service.dto.AuctionArtRegisterRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class AuctionArtRegisterRequest {
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

    public AuctionArtRegisterRequestDto toServiceDto() {
        return new AuctionArtRegisterRequestDto(
                this.userId,
                this.name,
                this.description,
                this.initPrice,
                this.file,
                this.startDate,
                this.endDate,
                this.hashtagList
        );
    }
}
