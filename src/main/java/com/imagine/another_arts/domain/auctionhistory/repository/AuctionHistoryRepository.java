package com.imagine.another_arts.domain.auctionhistory.repository;

import com.imagine.another_arts.domain.auctionhistory.AuctionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, Long> {

}
