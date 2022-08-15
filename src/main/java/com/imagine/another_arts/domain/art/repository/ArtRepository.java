package com.imagine.another_arts.domain.art.repository;

import com.imagine.another_arts.domain.art.Art;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArtRepository extends JpaRepository<Art, Long> {

    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.id = :artId")
    Optional<Art> findFirstArtBy(@Param("artId") Long artId);

    Optional<Art> findFirstByIdNotAndName(Long artId, String name);

    // RegisterDate or BidPrice or BidCount 기준 정렬 (N+1로 인해서 정렬 조회 시 Art-User 따로 fetch join)
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'AUCTION'")
    List<Art> findBySaleTypeAuction();

    // RegisterDateDESC
    @Query(value = "SELECT a.*, u.nickname, u.school_name, COUNT(la.id)" +
                    " FROM art a" +
                    " INNER JOIN users u on a.user_id = u.user_id" +
                    " INNER JOIN art_hashtag ah on ah.art_id = a.art_id" +
                    " INNER JOIN hashtag h on ah.hashtag_id = h.hashtag_id" +
                    " LEFT OUTER JOIN like_art la on a.art_id = la.art_id" +
                    " WHERE a.sale_type = 'GENERAL'" +
                    "       AND :hashtag IN(h.name)" +
                    " GROUP BY a.art_id, a.user_id, a.name, a.description, a.init_price, a.sale_type, a.register_date," +
                    "          a.upload_name, a.storage_name, u.user_id, u.nickname, u.school_name" +
                    " ORDER BY a.register_date DESC",
            nativeQuery = true)
    Slice<Art> findBySaleTypeGeneralAndHashtagOrderByRegisterDateDesc(@Param("hashtag") String hashtag, Pageable pageable);

    // RegisterDateASC
    @Query(value = "SELECT a.*, u.nickname, u.school_name, COUNT(la.id)" +
                    " FROM art a" +
                    " INNER JOIN users u on a.user_id = u.user_id" +
                    " INNER JOIN art_hashtag ah on ah.art_id = a.art_id" +
                    " INNER JOIN hashtag h on ah.hashtag_id = h.hashtag_id" +
                    " LEFT OUTER JOIN like_art la on a.art_id = la.art_id" +
                    " WHERE a.sale_type = 'GENERAL'" +
                    "       AND :hashtag IN(h.name)" +
                    " GROUP BY a.art_id, a.user_id, a.name, a.description, a.init_price, a.sale_type, a.register_date," +
                    "          a.upload_name, a.storage_name, u.user_id, u.nickname, u.school_name" +
                    " ORDER BY a.register_date",
            nativeQuery = true)
    Slice<Art> findBySaleTypeGeneralAndHashtagOrderByRegisterDateAsc(@Param("hashtag") String hashtag, Pageable pageable);

    // InitPriceDESC
    @Query(value = "SELECT a.*, u.nickname, u.school_name, COUNT(la.id)" +
                    " FROM art a" +
                    " INNER JOIN users u on a.user_id = u.user_id" +
                    " INNER JOIN art_hashtag ah on ah.art_id = a.art_id" +
                    " INNER JOIN hashtag h on ah.hashtag_id = h.hashtag_id" +
                    " LEFT OUTER JOIN like_art la on a.art_id = la.art_id" +
                    " WHERE a.sale_type = 'GENERAL'" +
                    "       AND :hashtag IN(h.name)" +
                    " GROUP BY a.art_id, a.user_id, a.name, a.description, a.init_price, a.sale_type, a.register_date," +
                    "          a.upload_name, a.storage_name, u.user_id, u.nickname, u.school_name" +
                    " ORDER BY a.init_price DESC, a.register_date DESC",
            nativeQuery = true)
    Slice<Art> findBySaleTypeGeneralAndHashtagOrderByInitPriceDesc(@Param("hashtag") String hashtag, Pageable pageable);

    // InitPriceASC
    @Query(value = "SELECT a.*, u.nickname, u.school_name, COUNT(la.id)" +
                    " FROM art a" +
                    " INNER JOIN users u on a.user_id = u.user_id" +
                    " INNER JOIN art_hashtag ah on ah.art_id = a.art_id" +
                    " INNER JOIN hashtag h on ah.hashtag_id = h.hashtag_id" +
                    " LEFT OUTER JOIN like_art la on a.art_id = la.art_id" +
                    " WHERE a.sale_type = 'GENERAL'" +
                    "       AND :hashtag IN(h.name)" +
                    " GROUP BY a.art_id, a.user_id, a.name, a.description, a.init_price, a.sale_type, a.register_date," +
                    "          a.upload_name, a.storage_name, u.user_id, u.nickname, u.school_name" +
                    " ORDER BY a.init_price, a.register_date DESC",
            nativeQuery = true)
    Slice<Art> findBySaleTypeGeneralAndHashtagOrderByInitPriceAsc(@Param("hashtag") String hashtag, Pageable pageable);

    // LikeArtDESC
    @Query(value = "SELECT a.*, u.nickname, u.school_name, COUNT(la.id)" +
                    " FROM art a" +
                    " INNER JOIN users u on a.user_id = u.user_id" +
                    " INNER JOIN art_hashtag ah on ah.art_id = a.art_id" +
                    " INNER JOIN hashtag h on ah.hashtag_id = h.hashtag_id" +
                    " LEFT OUTER JOIN like_art la on a.art_id = la.art_id" +
                    " WHERE a.sale_type = 'GENERAL'" +
                    "       AND :hashtag IN(h.name)" +
                    " GROUP BY a.art_id, a.user_id, a.name, a.description, a.init_price, a.sale_type, a.register_date," +
                    "          a.upload_name, a.storage_name, u.user_id, u.nickname, u.school_name" +
                    " ORDER BY COUNT(la.id) DESC, a.register_date DESC",
            nativeQuery = true)
    Slice<Art> findBySaleTypeGeneralAndHashtagOrderByLikeArtCountDesc(@Param("hashtag") String hashtag, Pageable pageable);

    // LikeArtASC
    @Query(value = "SELECT a.*, u.nickname, u.school_name, COUNT(la.id)" +
                    " FROM art a" +
                    " INNER JOIN users u on a.user_id = u.user_id" +
                    " INNER JOIN art_hashtag ah on ah.art_id = a.art_id" +
                    " INNER JOIN hashtag h on ah.hashtag_id = h.hashtag_id" +
                    " LEFT OUTER JOIN like_art la on a.art_id = la.art_id" +
                    " WHERE a.sale_type = 'GENERAL'" +
                    "       AND :hashtag IN(h.name)" +
                    " GROUP BY a.art_id, a.user_id, a.name, a.description, a.init_price, a.sale_type, a.register_date," +
                    "          a.upload_name, a.storage_name, u.user_id, u.nickname, u.school_name" +
                    " ORDER BY COUNT(la.id), a.register_date DESC",
            nativeQuery = true)
    Slice<Art> findBySaleTypeGeneralAndHashtagOrderByLikeArtCountAsc(@Param("hashtag") String hashtag, Pageable pageable);
}
