package com.imagine.another_arts.query;

public class AuctionArtQueryFactory {

    /**
     * 메인 페이지 경매 작품 페이징
     */
    public static final String MAIN_QUERY =
            "SELECT ac.auction_id as auctionId, u2.user_id as highestBidUserId, u2.nickname as highestBidUserNickname," +
            "       u2.school_name as highestBidUserSchoolName, ac.bid_price as highestBidPrice, ac.start_date as auctionStartDate, ac.end_date as auctionEndDate," +
            "       a.art_id as artId, a.name as artName, a.description as artDescription, a.init_price as artInitPrice," +
            "       a.register_date as artRegisterDate, a.storage_name as artStorageName," +
            "       u1.user_id as artOwnerId, u1.nickname as artOwnerNickname, u1.school_name as artOwnerSchoolName" +
            " FROM auction ac" +
            " INNER JOIN art a on ac.art_id = a.art_id" +
            " INNER JOIN users u1 on a.user_id = u1.user_id" +
            " LEFT OUTER JOIN users u2 on ac.user_id = u2.user_id";

    public static final String MAIN_ORDER_BY_DATE =
            " WHERE a.sale_type = 'AUCTION'" +
            "       AND ac.start_date <= CURRENT_TIMESTAMP" +
            "       AND ac.end_date >= CURRENT_TIMESTAMP" +
            " ORDER BY a.register_date";

    public static final String MAIN_ORDER_BY_BID_PRICE =
            " WHERE a.sale_type = 'AUCTION'" +
            "       AND ac.start_date <= CURRENT_TIMESTAMP" +
            "       AND ac.end_date >= CURRENT_TIMESTAMP" +
            " ORDER BY ac.bid_price";

    public static final String MAIN_ORDER_BY_BID_COUNT =
            " LEFT OUTER JOIN auction_history ach on ac.auction_id = ach.auction_id" +
            " WHERE a.sale_type = 'AUCTION'" +
            "       AND ac.start_date <= CURRENT_TIMESTAMP" +
            "       AND ac.end_date >= CURRENT_TIMESTAMP" +
            " GROUP BY ac.auction_id, u2.user_id, u2.nickname, u2.school_name, ac.bid_price, ac.start_date, ac.end_date," +
            "           a.art_id, a.name, a.description, a.init_price, a.register_date, a.storage_name," +
            "           u1.user_id, u1.nickname, u1.school_name" +
            " ORDER BY count(ach.id)";

    /**
     * 해시태그 기반 경매 작품 페이징
     */
    public static final String HASHTAG_QUERY =
            "SELECT ac.auction_id as auctionId, u2.user_id as highestBidUserId, u2.nickname as highestBidUserNickname," +
            "       u2.school_name as highestBidUserSchoolName, ac.bid_price as highestBidPrice, ac.start_date as auctionStartDate, ac.end_date as auctionEndDate," +
            "       a.art_id as artId, a.name as artName, a.description as artDescription, a.init_price as artInitPrice," +
            "       a.register_date as artRegisterDate, a.storage_name as artStorageName," +
            "       u1.user_id as artOwnerId, u1.nickname as artOwnerNickname, u1.school_name as artOwnerSchoolName" +
            " FROM auction ac" +
            " INNER JOIN art a on ac.art_id = a.art_id" +
            " INNER JOIN users u1 on a.user_id = u1.user_id" +
            " LEFT OUTER JOIN users u2 on ac.user_id = u2.user_id";

    public static final String HASHTAG_ORDER_BY_DATE =
            " INNER JOIN art_hashtag ah on a.art_id = ah.art_id" +
            " INNER JOIN hashtag h on ah.hashtag_id = h.hashtag_id" +
            " WHERE a.sale_type = 'AUCTION'" +
            "       AND ac.start_date <= CURRENT_TIMESTAMP" +
            "       AND ac.end_date >= CURRENT_TIMESTAMP" +
            "       AND :hashtag IN(h.name)" +
            " ORDER BY a.register_date";

    public static final String HASHTAG_ORDER_BY_BID_PRICE =
            " INNER JOIN art_hashtag ah on a.art_id = ah.art_id" +
            " INNER JOIN hashtag h on ah.hashtag_id = h.hashtag_id" +
            " WHERE a.sale_type = 'AUCTION'" +
            "       AND ac.start_date <= CURRENT_TIMESTAMP" +
            "       AND ac.end_date >= CURRENT_TIMESTAMP" +
            "       AND :hashtag IN(h.name)" +
            " ORDER BY ac.bid_price";

    public static final String HASHTAG_ORDER_BY_BID_COUNT =
            " LEFT OUTER JOIN auction_history ach on ac.auction_id = ach.auction_id" +
            " INNER JOIN art_hashtag ah on a.art_id = ah.art_id" +
            " INNER JOIN hashtag h on ah.hashtag_id = h.hashtag_id" +
            " WHERE a.sale_type = 'AUCTION'" +
            "       AND ac.start_date <= CURRENT_TIMESTAMP" +
            "       AND ac.end_date >= CURRENT_TIMESTAMP" +
            "       AND :hashtag IN(h.name)" +
            " GROUP BY ac.auction_id, u2.user_id, u2.nickname, u2.school_name, ac.bid_price, ac.start_date, ac.end_date," +
            "           a.art_id, a.name, a.description, a.init_price, a.register_date, a.storage_name," +
            "           u1.user_id, u1.nickname, u1.school_name" +
            " ORDER BY count(ach.id)";

    /**
     * 키워드 기반 경매 작품 페이징
     */
    public static final String KEYWORD_QUERY =
            "SELECT ac.auction_id as auctionId, u2.user_id as highestBidUserId, u2.nickname as highestBidUserNickname," +
            "       u2.school_name as highestBidUserSchoolName, ac.bid_price as highestBidPrice, ac.start_date as auctionStartDate, ac.end_date as auctionEndDate," +
            "       a.art_id as artId, a.name as artName, a.description as artDescription, a.init_price as artInitPrice," +
            "       a.register_date as artRegisterDate, a.storage_name as artStorageName," +
            "       u1.user_id as artOwnerId, u1.nickname as artOwnerNickname, u1.school_name as artOwnerSchoolName" +
            " FROM auction ac" +
            " INNER JOIN art a on ac.art_id = a.art_id" +
            " INNER JOIN users u1 on a.user_id = u1.user_id" +
            " LEFT OUTER JOIN users u2 on ac.user_id = u2.user_id";

    public static final String KEYWORD_ORDER_BY_DATE =
            " WHERE a.sale_type = 'AUCTION'" +
            "       AND ac.start_date <= CURRENT_TIMESTAMP" +
            "       AND ac.end_date >= CURRENT_TIMESTAMP" +
            "       AND (a.description LIKE :keyword OR a.name LIKE :keyword)"  +
            " ORDER BY a.register_date";

    public static final String KEYWORD_ORDER_BY_BID_PRICE =
            " WHERE a.sale_type = 'AUCTION'" +
            "       AND ac.start_date <= CURRENT_TIMESTAMP" +
            "       AND ac.end_date >= CURRENT_TIMESTAMP" +
            "       AND (a.description LIKE :keyword OR a.name LIKE :keyword)"  +
            " ORDER BY ac.bid_price";

    public static final String KEYWORD_ORDER_BY_BID_COUNT =
            " LEFT OUTER JOIN auction_history ach on ac.auction_id = ach.auction_id" +
            " WHERE a.sale_type = 'AUCTION'" +
            "       AND ac.start_date <= CURRENT_TIMESTAMP" +
            "       AND ac.end_date >= CURRENT_TIMESTAMP" +
            "       AND (a.description LIKE :keyword OR a.name LIKE :keyword)"  +
            " GROUP BY ac.auction_id, u2.user_id, u2.nickname, u2.school_name, ac.bid_price, ac.start_date, ac.end_date," +
            "           a.art_id, a.name, a.description, a.init_price, a.register_date, a.storage_name," +
            "           u1.user_id, u1.nickname, u1.school_name" +
            " ORDER BY count(ach.id)";
}
