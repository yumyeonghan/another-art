package com.imagine.another_arts.domain.auctionhistory.repository;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.auctionhistory.AuctionHistory;
import com.imagine.another_arts.domain.auctionhistory.repository.custom.AuctionHistoryQueryDSLRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, Long>, AuctionHistoryQueryDSLRepository {
    boolean existsByArtAndUserIsNotNull(Art art);
}
