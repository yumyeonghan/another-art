package com.imagine.another_arts.domain.auction.repository;

import com.imagine.another_arts.domain.auction.Auction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    // RegisterDateDESC
    @Query(value = "SELECT *" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " RIGHT OUTER JOIN users u on a.user_id = u.user_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    " ORDER BY a.register_date DESC",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionOrderByRegisterDateDesc(Pageable pageable);

    @Query(value = "SELECT *" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " RIGHT OUTER JOIN users u on a.user_id = u.user_id " +
                    " INNER JOIN hashtag_list hl on a.art_id = hl.art_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    "       AND :hashtag IN(hl.hashtag)" +
                    " ORDER BY a.register_date DESC",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionAndHashtagOrderByRegisterDateDesc(@Param("hashtag") String hashtag, Pageable pageable);

    // RegisterDateASC
    @Query(value = "SELECT *" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " RIGHT OUTER JOIN users u on a.user_id = u.user_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    " ORDER BY a.register_date",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionOrderByRegisterDateAsc(Pageable pageable);

    @Query(value = "SELECT *" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " RIGHT OUTER JOIN users u on a.user_id = u.user_id " +
                    " INNER JOIN hashtag_list hl on a.art_id = hl.art_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    "       AND :hashtag IN(hl.hashtag)" +
                    " ORDER BY a.register_date",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionAndHashtagOrderByRegisterDateAsc(@Param("hashtag") String hashtag, Pageable pageable);

    // BidPriceDESC
    @Query(value = "SELECT *" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " RIGHT OUTER JOIN users u on a.user_id = u.user_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    " ORDER BY ac.bid_price DESC",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionOrderByBidPriceDesc(Pageable pageable);

    @Query(value = "SELECT *" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " RIGHT OUTER JOIN users u on a.user_id = u.user_id " +
                    " INNER JOIN hashtag_list hl on a.art_id = hl.art_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    "       AND :hashtag IN(hl.hashtag)" +
                    " ORDER BY ac.bid_price DESC",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionAndHashtagOrderByBidPriceDesc(@Param("hashtag") String hashtag, Pageable pageable);

    // BidPriceASC
    @Query(value = "SELECT *" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " RIGHT OUTER JOIN users u on a.user_id = u.user_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    " ORDER BY ac.bid_price",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionOrderByBidPriceAsc(Pageable pageable);

    @Query(value = "SELECT *" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " RIGHT OUTER JOIN users u on a.user_id = u.user_id " +
                    " INNER JOIN hashtag_list hl on a.art_id = hl.art_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    "       AND :hashtag IN(hl.hashtag)" +
                    " ORDER BY ac.bid_price",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionAndHashtagOrderByBidPriceAsc(@Param("hashtag") String hashtag, Pageable pageable);

    // BidCountDESC
    @Query(value = "SELECT ac.*, a.*, u.*, COUNT(ah.id)" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " RIGHT OUTER JOIN users u on a.user_id = u.user_id" +
                    " LEFT OUTER JOIN auction_history ah on ac.auction_id = ah.auction_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    " GROUP BY ac.bid_price, ac.end_date, ac.start_date, ac.art_id, ac.auction_id, ac.user_id," +
                    "          a.register_date, a.user_id, a.sale_type, a.name, a.description, a.init_price, a.storage_name, a.upload_name," +
                    "          u.name, u.login_id, u.email, u.phone_number, u.address, u.birth, u.login_password, u.nickname, u.school_name" +
                    " ORDER BY count(ah.id) DESC",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionOrderByAuctionHistoryCountDesc(Pageable pageable);

    @Query(value = "SELECT ac.*, a.*, u.*, COUNT(ah.id)" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " RIGHT OUTER JOIN users u on a.user_id = u.user_id" +
                    " LEFT OUTER JOIN auction_history ah on ac.auction_id = ah.auction_id" +
                    " INNER JOIN hashtag_list hl on a.art_id = hl.art_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    "       AND :hashtag IN(hl.hashtag)" +
                    " GROUP BY ac.bid_price, ac.end_date, ac.start_date, ac.art_id, ac.auction_id, ac.user_id," +
                    "          a.register_date, a.user_id, a.sale_type, a.name, a.description, a.init_price, a.storage_name, a.upload_name," +
                    "          u.name, u.login_id, u.email, u.phone_number, u.address, u.birth, u.login_password, u.nickname, u.school_name" +
                    " ORDER BY count(ah.id) DESC",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionAndHashtagOrderByAuctionHistoryCountDesc(@Param("hashtag") String hashtag, Pageable pageable);

    // BidCountASC
    @Query(value = "SELECT ac.*, a.*, u.*, COUNT(ah.id)" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " RIGHT OUTER JOIN users u on a.user_id = u.user_id" +
                    " LEFT OUTER JOIN auction_history ah on ac.auction_id = ah.auction_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    " GROUP BY ac.bid_price, ac.end_date, ac.start_date, ac.art_id, ac.auction_id, ac.user_id," +
                    "          a.register_date, a.user_id, a.sale_type, a.name, a.description, a.init_price, a.storage_name, a.upload_name," +
                    "          u.name, u.login_id, u.email, u.phone_number, u.address, u.birth, u.login_password, u.nickname, u.school_name" +
                    " ORDER BY count(ah.id)",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionOrderByAuctionHistoryCountAsc(Pageable pageable);

    @Query(value = "SELECT ac.*, a.*, u.*, COUNT(ah.id)" +
                    " FROM auction ac" +
                    " INNER JOIN art a on ac.art_id = a.art_id" +
                    " RIGHT OUTER JOIN users u on a.user_id = u.user_id" +
                    " LEFT OUTER JOIN auction_history ah on ac.auction_id = ah.auction_id" +
                    " INNER JOIN hashtag_list hl on a.art_id = hl.art_id" +
                    " WHERE a.sale_type = 'AUCTION'" +
                    "       AND ac.start_date <= CURRENT_TIMESTAMP" +
                    "       AND ac.end_date >= CURRENT_TIMESTAMP" +
                    "       AND :hashtag IN(hl.hashtag)" +
                    " GROUP BY ac.bid_price, ac.end_date, ac.start_date, ac.art_id, ac.auction_id, ac.user_id," +
                    "          a.register_date, a.user_id, a.sale_type, a.name, a.description, a.init_price, a.storage_name, a.upload_name," +
                    "          u.name, u.login_id, u.email, u.phone_number, u.address, u.birth, u.login_password, u.nickname, u.school_name" +
                    " ORDER BY count(ah.id)",
            nativeQuery = true)
    Slice<Auction> findBySaleTypeAuctionAndHashtagOrderByAuctionHistoryCountAsc(@Param("hashtag") String hashtag, Pageable pageable);
}
