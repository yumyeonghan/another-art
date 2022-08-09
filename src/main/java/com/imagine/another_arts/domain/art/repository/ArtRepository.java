package com.imagine.another_arts.domain.art.repository;

import com.imagine.another_arts.domain.art.Art;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtRepository extends JpaRepository<Art, Long> {

    // RegisterDate or BidPrice or BidCount 기준 정렬 (N+1로 인해서 정렬 조회 시 Art-User 따로 fetch join)
    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.saleType = 'AUCTION'")
    List<Art> findAuctionArtList();
}
