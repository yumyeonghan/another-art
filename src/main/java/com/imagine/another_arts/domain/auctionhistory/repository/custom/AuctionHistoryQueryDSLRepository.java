package com.imagine.another_arts.domain.auctionhistory.repository.custom;

import com.imagine.another_arts.domain.auctionhistory.AuctionHistory;

import java.util.List;

public interface AuctionHistoryQueryDSLRepository {
    List<AuctionHistory> findAuctionHistoryList();

    Long getAuctionHistoryCountByArtId(Long artId);
}
