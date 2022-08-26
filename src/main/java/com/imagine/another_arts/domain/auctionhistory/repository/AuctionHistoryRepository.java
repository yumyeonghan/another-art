package com.imagine.another_arts.domain.auctionhistory.repository;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.auctionhistory.AuctionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, Long> {
    @Query("SELECT ah" +
            " FROM AuctionHistory ah" +
            " JOIN FETCH ah.art" +
            " JOIN FETCH ah.user" +
            " JOIN FETCH ah.auction")
    List<AuctionHistory> findAuctionHistoriesBy();

    boolean existsByArtAndUserIsNotNull(Art art);
}
