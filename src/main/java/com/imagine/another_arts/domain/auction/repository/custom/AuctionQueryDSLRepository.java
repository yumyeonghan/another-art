package com.imagine.another_arts.domain.auction.repository.custom;

import com.imagine.another_arts.domain.auction.Auction;

import java.util.Optional;

public interface AuctionQueryDSLRepository {
    Optional<Auction> findAuctionByAuctionId(Long auctionId);
}
