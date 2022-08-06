package com.imagine.another_arts.domain.auction.repository;

import com.imagine.another_arts.domain.auction.Auction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    // RegisterDateDESC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            " ORDER BY ac.art.registerDate DESC")
    Slice<Auction> findAuctionArtSortByRegisterDateDESC(Pageable pageable);

    // RegisterDateASC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            " ORDER BY ac.art.registerDate")
    Slice<Auction> findAuctionArtSortByRegisterDateASC(Pageable pageable);

    // BidPriceDESC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            " ORDER BY ac.bidPrice DESC")
    Slice<Auction> findAuctionArtSortByBidPriceDESC(Pageable pageable);

    // BidPriceASC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            " ORDER BY ac.bidPrice")
    Slice<Auction> findAuctionArtSortByBidPriceASC(Pageable pageable);

    // BidCountDESC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            " ORDER BY size(ac.auctionHistoryList) DESC")
    Slice<Auction> findAuctionArtSortByBidCountDESC(Pageable pageable);

    // BidCountASC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            " ORDER BY size(ac.auctionHistoryList)")
    Slice<Auction> findAuctionArtSortByBidCountASC(Pageable pageable);
}
