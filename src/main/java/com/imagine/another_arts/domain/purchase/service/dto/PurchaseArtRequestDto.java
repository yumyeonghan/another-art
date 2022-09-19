package com.imagine.another_arts.domain.purchase.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseArtRequestDto {
    private Long artId;
    private Long userId;
}
