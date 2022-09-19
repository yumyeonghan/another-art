package com.imagine.another_arts.web.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseArtResponse {
    private Long artId;
    private Long userId;
}
