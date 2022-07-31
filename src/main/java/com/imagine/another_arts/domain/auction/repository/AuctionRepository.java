package com.imagine.another_arts.domain.auction.repository;

import com.imagine.another_arts.domain.auction.Auction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    // RegisterDate 기준 정렬
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION' AND ac.art.id < :lastArtId" +
            " ORDER BY ac.art.registerDate DESC")
    Slice<Auction> findAuctionArtSortByRegisterDate(@Param("lastArtId") Long lastArtId, Pageable pageable);

    // BidPrice 기준 정렬
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            " ORDER BY ac.bidPrice DESC")
    Slice<Auction> findAuctionArtSortByBidPrice(Pageable pageable);

    // BidCount 기준 정렬
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            " ORDER BY size(ac.auctionHistoryList) DESC")
    Slice<Auction> findAuctionArtSortByBidCount(Pageable pageable);
}
