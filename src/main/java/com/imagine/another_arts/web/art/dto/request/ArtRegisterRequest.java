package com.imagine.another_arts.web.art.dto.request;

import com.imagine.another_arts.domain.art.service.dto.request.ArtRegisterRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtRegisterRequest {
    private String saleType;
    private Long userId;
    private String name;
    private String description;
    private Long initPrice;
    private MultipartFile file;
    private List<String> hashtagList;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    public ArtRegisterRequestDto toAuctioArtDto() {
        return new ArtRegisterRequestDto(
                this.saleType,
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

    public ArtRegisterRequestDto toGeneralArtDto() {
        return new ArtRegisterRequestDto(
                this.saleType,
                this.userId,
                this.name,
                this.description,
                this.initPrice,
                this.file,
                this.hashtagList
        );
    }
}
