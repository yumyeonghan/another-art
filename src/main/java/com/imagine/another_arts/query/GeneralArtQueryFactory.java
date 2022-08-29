package com.imagine.another_arts.query;

public class GeneralArtQueryFactory {

    /**
     * 해시태그 기반 일반 작품 페이징
     */
    public static final String HASHTAG_QUERY =
            "SELECT a.art_id as artId, a.name as artName, a.description as artDescription, a.init_price as artInitPrice," +
            "       a.register_date as artRegisterDate, a.storage_name as artStorageName," +
            "       u.user_id as artOwnerId, u.nickname as artOwnerNickname, u.school_name as artOwnerSchoolName" +
            " FROM art a" +
            " INNER JOIN users u on a.user_id = u.user_id" +
            " INNER JOIN art_hashtag ah on ah.art_id = a.art_id" +
            " INNER JOIN hashtag h on ah.hashtag_id = h.hashtag_id";

    public static final String HASHTAG_ORDER_BY_DATE =
            " WHERE a.sale_type = 'GENERAL'" +
            "       AND :hashtag IN(h.name)" +
            " ORDER BY a.register_date";

    public static final String HASHTAG_ORDER_BY_PRICE =
            " WHERE a.sale_type = 'GENERAL'" +
            "       AND :hashtag IN(h.name)" +
            " ORDER BY a.init_price";

    public static final String HASHTAG_ORDER_BY_LIKE_COUNT =
            " LEFT OUTER JOIN like_art la on a.art_id = la.art_id" +
            " WHERE a.sale_type = 'GENERAL'" +
            "       AND :hashtag IN(h.name)" +
            " GROUP BY a.art_id, a.name, a.description, a.init_price, a.register_date, a.storage_name," +
            "           u.user_id, u.nickname, u.school_name" +
            " ORDER BY COUNT(la.id)";

    /**
     * 키워드 기반 일반 작품 페이징
     */
    public static final String KEYWORD_QUERY =
            "SELECT a.art_id as artId, a.name as artName, a.description as artDescription, a.init_price as artInitPrice," +
            "       a.register_date as artRegisterDate, a.storage_name as artStorageName," +
            "       u.user_id as artOwnerId, u.nickname as artOwnerNickname, u.school_name as artOwnerSchoolName" +
            " FROM art a" +
            " INNER JOIN users u on a.user_id = u.user_id";

    public static final String KEYWORD_ORDER_BY_DATE =
            " WHERE a.sale_type = 'GENERAL'" +
            "       AND (a.description LIKE :keyword OR a.name LIKE :keyword)" +
            " ORDER BY a.register_date";

    public static final String KEYWORD_ORDER_BY_PRICE =
            " WHERE a.sale_type = 'GENERAL'" +
            "       AND (a.description LIKE :keyword OR a.name LIKE :keyword)" +
            " ORDER BY a.init_price";

    public static final String KEYWORD_ORDER_BY_LIKE_COUNT =
            " LEFT OUTER JOIN like_art la on a.art_id = la.art_id" +
            " WHERE a.sale_type = 'GENERAL'" +
            "       AND (a.description LIKE :keyword OR a.name LIKE :keyword)" +
            " GROUP BY a.art_id, a.name, a.description, a.init_price, a.register_date, a.storage_name," +
            "           u.user_id, u.nickname, u.school_name" +
            " ORDER BY COUNT(la.id)";

}
