package com.imagine.another_arts.domain.auction.repository;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.auction.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

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
    
    @Query("SELECT ac" +
              " FROM Auction ac" +
              " JOIN FETCH ac.user" +
              " JOIN FETCH ac.art" +
              " WHERE ac.id = :auctionId")
    Optional<Auction> findFirstById(@Param("auctionId") Long auctionId);
}
