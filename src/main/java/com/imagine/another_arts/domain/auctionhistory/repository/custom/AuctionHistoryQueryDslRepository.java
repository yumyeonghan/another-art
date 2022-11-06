package com.imagine.another_arts.domain.auctionhistory.repository.custom;

import com.imagine.another_arts.domain.auctionhistory.AuctionHistory;

import java.util.List;

public interface AuctionHistoryQueryDslRepository {
    List<AuctionHistory> findAllWithFetchArt();
    Long getAuctionHistoryCountByArtId(Long artId);
}
