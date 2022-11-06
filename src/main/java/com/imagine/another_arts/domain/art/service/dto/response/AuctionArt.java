package com.imagine.another_arts.domain.art.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AuctionArt extends AbstractArt {
    private Long auctionArtBidCount;
    private BasicAuctionArtTranslator auctionArt;
    private List<String> artHashtagList;
}
