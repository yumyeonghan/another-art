package com.imagine.another_arts.domain.art.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AuctionArtResponse extends ArtResponse {
    private Long auctionArtBidCount;
    private BasicAuctionArtResponse auctionArt;
    private List<String> artHashtagList;
}
