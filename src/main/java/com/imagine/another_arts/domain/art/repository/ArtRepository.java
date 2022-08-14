package com.imagine.another_arts.domain.art.repository;

import com.imagine.another_arts.domain.art.Art;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtRepository extends JpaRepository<Art, Long> {

    // RegisterDate or BidPrice or BidCount 기준 정렬 (N+1로 인해서 정렬 조회 시 Art-User 따로 fetch join)
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'AUCTION'")
    List<Art> findBySaleTypeAuction();

    // RegisterDateDESC
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'GENERAL'" +
            "       AND :hashtag MEMBER OF a.hashtagList"+
            " ORDER BY a.registerDate DESC")
    Slice<Art> findBySaleTypeGeneralAndHashtagOrderByRegisterDateDesc(@Param("hashtag") String hashtag, Pageable pageable);

    // RegisterDateASC
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'GENERAL'" +
            "       AND :hashtag MEMBER OF a.hashtagList"+
            " ORDER BY a.registerDate")
    Slice<Art> findBySaleTypeGeneralAndHashtagOrderByRegisterDateAsc(@Param("hashtag") String hashtag, Pageable pageable);

    // InitPriceDESC
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'GENERAL'" +
            "       AND :hashtag MEMBER OF a.hashtagList"+
            " ORDER BY a.initPrice DESC")
    Slice<Art> findBySaleTypeGeneralAndHashtagOrderByInitPriceDesc(@Param("hashtag") String hashtag, Pageable pageable);

    // InitPriceASC
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'GENERAL'" +
            "       AND :hashtag MEMBER OF a.hashtagList"+
            " ORDER BY a.initPrice")
    Slice<Art> findBySaleTypeGeneralAndHashtagOrderByInitPriceAsc(@Param("hashtag") String hashtag, Pageable pageable);

    // LikeArtDESC
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'GENERAL'" +
            "       AND :hashtag MEMBER OF a.hashtagList"+
            " ORDER BY size(a.likeArtList) DESC")
    Slice<Art> findBySaleTypeGeneralAndHashtagOrderByLikeArtCountDesc(@Param("hashtag") String hashtag, Pageable pageable);

    // LikeArtASC
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'GENERAL'" +
            "       AND :hashtag MEMBER OF a.hashtagList"+
            " ORDER BY size(a.likeArtList)")
    Slice<Art> findBySaleTypeGeneralAndHashtagOrderByLikeArtCountAsc(@Param("hashtag") String hashtag, Pageable pageable);
}
