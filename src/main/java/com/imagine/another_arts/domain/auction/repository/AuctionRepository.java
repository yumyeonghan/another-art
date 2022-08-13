package com.imagine.another_arts.domain.auction.repository;

import com.imagine.another_arts.domain.auction.Auction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    // RegisterDateDESC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            "       AND ac.startDate <= CURRENT_TIMESTAMP" +
            "       AND ac.endDate >= CURRENT_TIMESTAMP"+
            " ORDER BY ac.art.registerDate DESC")
    Slice<Auction> findAuctionArtSortByRegisterDateDESC(Pageable pageable);

    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            "       AND ac.startDate <= CURRENT_TIMESTAMP" +
            "       AND ac.endDate >= CURRENT_TIMESTAMP"+
            "       AND :hashtag MEMBER OF ac.art.hashtagList"+
            " ORDER BY ac.art.registerDate DESC")
    Slice<Auction> findAuctionArtSortByRegisterDateDESCWithHashtag(@Param("hashtag") String hashtag, Pageable pageable);

    // RegisterDateASC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            "       AND ac.startDate <= CURRENT_TIMESTAMP" +
            "       AND ac.endDate >= CURRENT_TIMESTAMP" +
            " ORDER BY ac.art.registerDate")
    Slice<Auction> findAuctionArtSortByRegisterDateASC(Pageable pageable);

    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            "       AND ac.startDate <= CURRENT_TIMESTAMP" +
            "       AND ac.endDate >= CURRENT_TIMESTAMP" +
            "       AND :hashtag MEMBER OF ac.art.hashtagList"+
            " ORDER BY ac.art.registerDate")
    Slice<Auction> findAuctionArtSortByRegisterDateASCWithHashtag(@Param("hashtag") String hashtag, Pageable pageable);

    // BidPriceDESC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            "       AND ac.startDate <= CURRENT_TIMESTAMP" +
            "       AND ac.endDate >= CURRENT_TIMESTAMP" +
            " ORDER BY ac.bidPrice DESC")
    Slice<Auction> findAuctionArtSortByBidPriceDESC(Pageable pageable);

    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            "       AND ac.startDate <= CURRENT_TIMESTAMP" +
            "       AND ac.endDate >= CURRENT_TIMESTAMP" +
            "       AND :hashtag MEMBER OF ac.art.hashtagList"+
            " ORDER BY ac.bidPrice DESC")
    Slice<Auction> findAuctionArtSortByBidPriceDESCWithHashtag(@Param("hashtag") String hashtag, Pageable pageable);

    // BidPriceASC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            "       AND ac.startDate <= CURRENT_TIMESTAMP" +
            "       AND ac.endDate >= CURRENT_TIMESTAMP" +
            " ORDER BY ac.bidPrice")
    Slice<Auction> findAuctionArtSortByBidPriceASC(Pageable pageable);

    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            "       AND ac.startDate <= CURRENT_TIMESTAMP" +
            "       AND ac.endDate >= CURRENT_TIMESTAMP" +
            "       AND :hashtag MEMBER OF ac.art.hashtagList"+
            " ORDER BY ac.bidPrice")
    Slice<Auction> findAuctionArtSortByBidPriceASCWithHashtag(@Param("hashtag") String hashtag, Pageable pageable);

    // BidCountDESC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            "       AND ac.startDate <= CURRENT_TIMESTAMP" +
            "       AND ac.endDate >= CURRENT_TIMESTAMP" +
            " ORDER BY size(ac.auctionHistoryList) DESC")
    Slice<Auction> findAuctionArtSortByBidCountDESC(Pageable pageable);

    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            "       AND ac.startDate <= CURRENT_TIMESTAMP" +
            "       AND ac.endDate >= CURRENT_TIMESTAMP" +
            "       AND :hashtag MEMBER OF ac.art.hashtagList"+
            " ORDER BY size(ac.auctionHistoryList) DESC")
    Slice<Auction> findAuctionArtSortByBidCountDESCWithHashtag(@Param("hashtag") String hashtag, Pageable pageable);

    // BidCountASC
    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            "       AND ac.startDate <= CURRENT_TIMESTAMP" +
            "       AND ac.endDate >= CURRENT_TIMESTAMP" +
            " ORDER BY size(ac.auctionHistoryList)")
    Slice<Auction> findAuctionArtSortByBidCountASC(Pageable pageable);

    @Query("SELECT DISTINCT ac" +
            " FROM Auction ac" +
            " JOIN FETCH ac.art JOIN FETCH ac.user" +
            " WHERE ac.art.saleType = 'AUCTION'" +
            "       AND ac.startDate <= CURRENT_TIMESTAMP" +
            "       AND ac.endDate >= CURRENT_TIMESTAMP" +
            "       AND :hashtag MEMBER OF ac.art.hashtagList"+
            " ORDER BY size(ac.auctionHistoryList)")
    Slice<Auction> findAuctionArtSortByBidCountASCWithHashtag(@Param("hashtag") String hashtag, Pageable pageable);
}
