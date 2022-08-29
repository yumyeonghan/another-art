package com.imagine.another_arts.domain.art.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class AuctionArtResponse {
    private Long auctionArtBidCount;
    private BasicAuctionArtResponse auctionArt;
    private List<String> artHashtagList = new ArrayList<>();
}
