package com.imagine.another_arts.domain.auction.repository;

import com.imagine.another_arts.domain.auction.Auction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    // RegisterDateDESC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION' AND ac.startDate <= :currentTime AND ac.endDate >= :currentTime"+
            " ORDER BY ac.art.registerDate DESC")
    Slice<Auction> findAuctionArtSortByRegisterDateDESC(@Param("currentTime") LocalDateTime currentTime, Pageable pageable);

    // RegisterDateASC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION' AND ac.startDate <= :currentTime AND ac.endDate >= :currentTime" +
            " ORDER BY ac.art.registerDate")
    Slice<Auction> findAuctionArtSortByRegisterDateASC(@Param("currentTime") LocalDateTime currentTime, Pageable pageable);

    // BidPriceDESC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION' AND ac.startDate <= :currentTime AND ac.endDate >= :currentTime" +
            " ORDER BY ac.bidPrice DESC")
    Slice<Auction> findAuctionArtSortByBidPriceDESC(@Param("currentTime") LocalDateTime currentTime, Pageable pageable);

    // BidPriceASC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION' AND ac.startDate <= :currentTime AND ac.endDate >= :currentTime" +
            " ORDER BY ac.bidPrice")
    Slice<Auction> findAuctionArtSortByBidPriceASC(@Param("currentTime") LocalDateTime currentTime, Pageable pageable);

    // BidCountDESC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION' AND ac.startDate <= :currentTime AND ac.endDate >= :currentTime" +
            " ORDER BY size(ac.auctionHistoryList) DESC")
    Slice<Auction> findAuctionArtSortByBidCountDESC(@Param("currentTime") LocalDateTime currentTime, Pageable pageable);

    // BidCountASC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION' AND ac.startDate <= :currentTime AND ac.endDate >= :currentTime" +
            " ORDER BY size(ac.auctionHistoryList)")
    Slice<Auction> findAuctionArtSortByBidCountASC(@Param("currentTime") LocalDateTime currentTime, Pageable pageable);
}
