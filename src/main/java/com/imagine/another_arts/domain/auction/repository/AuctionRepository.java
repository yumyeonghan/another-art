package com.imagine.another_arts.domain.auction.repository;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.auction.Auction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    Auction findByArt(Art art);

    @Query(value = "SELECT *" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " INNER JOIN users u1 on a.user_id = u1.user_id" +
                    " LEFT OUTER JOIN users u2 on ac.user_id = u2.user_id" +
                    " WHERE a.sale_type = 'AUCTION' AND a.art_id = :artId",
            nativeQuery = true)
    Auction findFirstAuctionBy(@Param("artId") Long artId);

    // RegisterDateDESC
    @Query(value = "SELECT ac.*, u1.user_id, u1.nickname, u1.school_name, a.art_id, a.name, a.description, a.init_price," +
                    " a.register_date, a.storage_name, u2.user_id, u2.nickname, u2.school_name" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " INNER JOIN users u1 on a.user_id = u1.user_id" +
                    " LEFT OUTER JOIN users u2 on ac.user_id = u2.user_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    " ORDER BY a.register_date DESC",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionOrderByRegisterDateDesc(Pageable pageable);

    @Query(value = "SELECT ac.*, u1.user_id, u1.nickname, u1.school_name, a.art_id, a.name, a.description, a.init_price," +
                    " a.register_date, a.storage_name, u2.user_id, u2.nickname, u2.school_name" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " INNER JOIN users u1 on a.user_id = u1.user_id" +
                    " LEFT OUTER JOIN users u2 on ac.user_id = u2.user_id" +
                    " INNER JOIN art_hashtag ah on a.art_id = ah.art_id" +
                    " INNER JOIN hashtag h on ah.hashtag_id = h.hashtag_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    "       AND :hashtag IN(h.name)" +
                    " ORDER BY a.register_date DESC",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionAndHashtagOrderByRegisterDateDesc(@Param("hashtag") String hashtag, Pageable pageable);

    // RegisterDateASC
    @Query(value = "SELECT ac.*, u1.user_id, u1.nickname, u1.school_name, a.art_id, a.name, a.description, a.init_price," +
                    " a.register_date, a.storage_name, u2.user_id, u2.nickname, u2.school_name" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " INNER JOIN users u1 on a.user_id = u1.user_id" +
                    " LEFT OUTER JOIN users u2 on ac.user_id = u2.user_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    " ORDER BY a.register_date",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionOrderByRegisterDateAsc(Pageable pageable);

    @Query(value = "SELECT ac.*, u1.user_id, u1.nickname, u1.school_name, a.art_id, a.name, a.description, a.init_price," +
                    " a.register_date, a.storage_name, u2.user_id, u2.nickname, u2.school_name" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " INNER JOIN users u1 on a.user_id = u1.user_id" +
                    " LEFT OUTER JOIN users u2 on ac.user_id = u2.user_id" +
                    " INNER JOIN art_hashtag ah on a.art_id = ah.art_id" +
                    " INNER JOIN hashtag h on ah.hashtag_id = h.hashtag_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    "       AND :hashtag IN(h.name)" +
                    " ORDER BY a.register_date",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionAndHashtagOrderByRegisterDateAsc(@Param("hashtag") String hashtag, Pageable pageable);

    // BidPriceDESC
    @Query(value = "SELECT ac.*, u1.user_id, u1.nickname, u1.school_name, a.art_id, a.name, a.description, a.init_price," +
                    " a.register_date, a.storage_name, u2.user_id, u2.nickname, u2.school_name" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " INNER JOIN users u1 on a.user_id = u1.user_id" +
                    " LEFT OUTER JOIN users u2 on ac.user_id = u2.user_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    " ORDER BY ac.bid_price DESC, a.register_date DESC",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionOrderByBidPriceDesc(Pageable pageable);

    @Query(value = "SELECT ac.*, u1.user_id, u1.nickname, u1.school_name, a.art_id, a.name, a.description, a.init_price," +
                    " a.register_date, a.storage_name, u2.user_id, u2.nickname, u2.school_name" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " INNER JOIN users u1 on a.user_id = u1.user_id" +
                    " LEFT OUTER JOIN users u2 on ac.user_id = u2.user_id" +
                    " INNER JOIN art_hashtag ah on a.art_id = ah.art_id" +
                    " INNER JOIN hashtag h on ah.hashtag_id = h.hashtag_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    "       AND :hashtag IN(h.name)" +
                    " ORDER BY ac.bid_price DESC, a.register_date DESC",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionAndHashtagOrderByBidPriceDesc(@Param("hashtag") String hashtag, Pageable pageable);

    // BidPriceASC
    @Query(value = "SELECT ac.*, u1.user_id, u1.nickname, u1.school_name, a.art_id, a.name, a.description, a.init_price," +
                    " a.register_date, a.storage_name, u2.user_id, u2.nickname, u2.school_name" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " INNER JOIN users u1 on a.user_id = u1.user_id" +
                    " LEFT OUTER JOIN users u2 on ac.user_id = u2.user_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    " ORDER BY ac.bid_price, a.register_date DESC",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionOrderByBidPriceAsc(Pageable pageable);

    @Query(value = "SELECT ac.*, u1.user_id, u1.nickname, u1.school_name, a.art_id, a.name, a.description, a.init_price," +
                    " a.register_date, a.storage_name, u2.user_id, u2.nickname, u2.school_name" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " INNER JOIN users u1 on a.user_id = u1.user_id" +
                    " LEFT OUTER JOIN users u2 on ac.user_id = u2.user_id" +
                    " INNER JOIN art_hashtag ah on a.art_id = ah.art_id" +
                    " INNER JOIN hashtag h on ah.hashtag_id = h.hashtag_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    "       AND :hashtag IN(h.name)" +
                    " ORDER BY ac.bid_price, a.register_date DESC",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionAndHashtagOrderByBidPriceAsc(@Param("hashtag") String hashtag, Pageable pageable);

    // BidCountDESC
    @Query(value = "SELECT ac.*, u1.user_id, u1.nickname, u1.school_name, a.art_id, a.name, a.description, a.init_price," +
                    " a.register_date, a.storage_name, u2.user_id, u2.nickname, u2.school_name, count(ach.id)" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " INNER JOIN users u1 on a.user_id = u1.user_id" +
                    " LEFT OUTER JOIN users u2 on ac.user_id = u2.user_id" +
                    " LEFT OUTER JOIN auction_history ach on ac.auction_id = ach.auction_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    " GROUP BY ac.bid_price, ac.end_date, ac.start_date, ac.art_id, ac.auction_id, ac.user_id," +
                    "          u1.user_id, u1.nickname, u1.school_name, a.art_id, a.name, a.description, a.init_price," +
                    "          a.register_date, a.storage_name, u2.user_id, u2.nickname, u2.school_name" +
                    " ORDER BY count(ach.id) DESC, a.register_date DESC",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionOrderByAuctionHistoryCountDesc(Pageable pageable);

    @Query(value = "SELECT ac.*, u1.user_id, u1.nickname, u1.school_name, a.art_id, a.name, a.description, a.init_price," +
                    " a.register_date, a.storage_name, u2.user_id, u2.nickname, u2.school_name, count(ach.id)" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " INNER JOIN users u1 on a.user_id = u1.user_id" +
                    " LEFT OUTER JOIN users u2 on ac.user_id = u2.user_id" +
                    " LEFT OUTER JOIN auction_history ach on ac.auction_id = ach.auction_id" +
                    " INNER JOIN art_hashtag ah on a.art_id = ah.art_id" +
                    " INNER JOIN hashtag h on ah.hashtag_id = h.hashtag_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    "       AND :hashtag IN(h.name)" +
                    " GROUP BY ac.bid_price, ac.end_date, ac.start_date, ac.art_id, ac.auction_id, ac.user_id," +
                    "          u1.user_id, u1.nickname, u1.school_name, a.art_id, a.name, a.description, a.init_price," +
                    "          a.register_date, a.storage_name, u2.user_id, u2.nickname, u2.school_name" +
                    " ORDER BY count(ach.id) DESC, a.register_date DESC",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionAndHashtagOrderByAuctionHistoryCountDesc(@Param("hashtag") String hashtag, Pageable pageable);

    // BidCountASC
    @Query(value = "SELECT ac.*, u1.user_id, u1.nickname, u1.school_name, a.art_id, a.name, a.description, a.init_price," +
                    " a.register_date, a.storage_name, u2.user_id, u2.nickname, u2.school_name, count(ach.id)" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " INNER JOIN users u1 on a.user_id = u1.user_id" +
                    " LEFT OUTER JOIN users u2 on ac.user_id = u2.user_id" +
                    " LEFT OUTER JOIN auction_history ach on ac.auction_id = ach.auction_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    " GROUP BY ac.bid_price, ac.end_date, ac.start_date, ac.art_id, ac.auction_id, ac.user_id," +
                    "          u1.user_id, u1.nickname, u1.school_name, a.art_id, a.name, a.description, a.init_price," +
                    "          a.register_date, a.storage_name, u2.user_id, u2.nickname, u2.school_name" +
                    " ORDER BY count(ach.id), a.register_date DESC",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionOrderByAuctionHistoryCountAsc(Pageable pageable);

    @Query(value = "SELECT ac.*, u1.user_id, u1.nickname, u1.school_name, a.art_id, a.name, a.description, a.init_price," +
                    " a.register_date, a.storage_name, u2.user_id, u2.nickname, u2.school_name, count(ach.id)" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " INNER JOIN users u1 on a.user_id = u1.user_id" +
                    " LEFT OUTER JOIN users u2 on ac.user_id = u2.user_id" +
                    " LEFT OUTER JOIN auction_history ach on ac.auction_id = ach.auction_id" +
                    " INNER JOIN art_hashtag ah on a.art_id = ah.art_id" +
                    " INNER JOIN hashtag h on ah.hashtag_id = h.hashtag_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    "       AND :hashtag IN(h.name)" +
                    " GROUP BY ac.bid_price, ac.end_date, ac.start_date, ac.art_id, ac.auction_id, ac.user_id," +
                    "          u1.user_id, u1.nickname, u1.school_name, a.art_id, a.name, a.description, a.init_price," +
                    "          a.register_date, a.storage_name, u2.user_id, u2.nickname, u2.school_name" +
                    " ORDER BY count(ach.id), a.register_date DESC",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionAndHashtagOrderByAuctionHistoryCountAsc(@Param("hashtag") String hashtag, Pageable pageable);
}
