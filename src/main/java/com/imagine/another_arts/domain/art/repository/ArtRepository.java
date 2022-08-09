package com.imagine.another_arts.domain.art.repository;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.auction.Auction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArtRepository extends JpaRepository<Art, Long> {

    // RegisterDate or BidPrice or BidCount 기준 정렬 (N+1로 인해서 정렬 조회 시 Art-User 따로 fetch join)
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'AUCTION'")
    List<Art> findAuctionArtList();

    // 일반 판매 작품 리스트 반환
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'GENERAL'")
    List<Art> findGeneralArtList();

    // RegisterDateDESC
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'GENERAL'" +
            " ORDER BY a.registerDate DESC")
    Slice<Art> findGeneralArtSortByRegisterDateDESC(Pageable pageable);

    // RegisterDateASC
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'GENERAL'" +
            " ORDER BY a.registerDate")
    Slice<Art> findGeneralArtSortByRegisterDateASC(Pageable pageable);

    // InitPriceDESC
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'GENERAL'" +
            " ORDER BY a.initPrice DESC")
    Slice<Art> findGeneralArtSortByInitPriceDESC(Pageable pageable);

    // InitPriceASC
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'GENERAL'" +
            " ORDER BY a.initPrice")
    Slice<Art> findGeneralArtSortByInitPriceASC(Pageable pageable);

    // LikeArtDESC
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'GENERAL'" +
            " ORDER BY size(a.likeArtList) DESC")
    Slice<Art> findGeneralArtSortByLikeArtDESC(Pageable pageable);

    // LikeArtASC
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'GENERAL'" +
            " ORDER BY size(a.likeArtList)")
    Slice<Art> findGeneralArtSortByLikeArtASC(Pageable pageable);
}
