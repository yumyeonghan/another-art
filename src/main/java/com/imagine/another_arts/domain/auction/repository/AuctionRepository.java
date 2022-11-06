package com.imagine.another_arts.domain.auction.repository;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.auction.Auction;
import com.imagine.another_arts.domain.auction.repository.custom.AuctionQueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long>, AuctionQueryDslRepository {
    Auction findAuctionByArt(Art art);
}
