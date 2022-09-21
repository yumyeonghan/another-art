package com.imagine.another_arts.domain.auction.service;

import com.imagine.another_arts.domain.auction.Auction;
import com.imagine.another_arts.domain.auction.repository.AuctionRepository;
import com.imagine.another_arts.domain.auction.service.dto.request.BidAuctionRequestDto;
import com.imagine.another_arts.domain.auctionhistory.AuctionHistory;
import com.imagine.another_arts.domain.auctionhistory.repository.AuctionHistoryRepository;
import com.imagine.another_arts.domain.user.User;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionHistoryRepository auctionHistoryRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    @Transactional
    public synchronized void updateHighestBidDetails(BidAuctionRequestDto bidAuctionRequest) {
        Auction currentAuction = auctionRepository.findAuctionByAuctionId(bidAuctionRequest.getAuctionId())
                .orElseThrow(() -> new AuctionNotFoundException("경매 정보가 존재하지 않습니다"));

        if(currentAuction.getEndDate().isBefore(LocalDateTime.now())) {
            throw new ClosedAuctionException("이미 종료된 경매입니다");
        } else if (currentAuction.getBidPrice() >= bidAuctionRequest.getBidPrice()) {
            throw new BidAmountNotEnoughException("입찰 금액이 부족합니다");
        }

        User currentBidUser = userRepository.findById(bidAuctionRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다"));

        if (currentBidUser.getAvailablePoint() < bidAuctionRequest.getBidPrice()) {
            throw new PointNotEnoughException("포인트가 충분하지 않습니다");
        }

        Optional<User> previousBidUser = Optional.ofNullable(currentAuction.getUser());
        previousBidUser.ifPresent(users -> users.updateAvailablePoint(users.getAvailablePoint() + currentAuction.getBidPrice()));
        currentBidUser.updateAvailablePoint(currentBidUser.getAvailablePoint() - bidAuctionRequest.getBidPrice());
        currentAuction.applyNewBid(currentBidUser, bidAuctionRequest.getBidPrice());

        auctionHistoryRepository.save(AuctionHistory.createAuctionHistory(
                currentAuction,
                currentAuction.getArt(),
                currentBidUser,
                bidAuctionRequest.getBidPrice()
        ));
    }
}
