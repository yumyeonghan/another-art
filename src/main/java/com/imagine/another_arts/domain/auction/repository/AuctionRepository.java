package com.imagine.another_arts.domain.auction.repository;

import com.imagine.another_arts.domain.auction.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

}
