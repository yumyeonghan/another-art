package com.imagine.another_arts.domain.auction.service;

import com.imagine.another_arts.domain.auction.Auction;
import com.imagine.another_arts.domain.auction.repository.AuctionRepository;
import com.imagine.another_arts.domain.auctionhistory.AuctionHistory;
import com.imagine.another_arts.domain.auctionhistory.repository.AuctionHistoryRepository;
import com.imagine.another_arts.domain.user.User;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.AnotherArtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.imagine.another_arts.exception.AnotherArtErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionService {
    private final AuctionHistoryRepository auctionHistoryRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    @Transactional
    public synchronized void updateHighestBidDetails(Long auctionId, Long userId, Long bidPrice) {
        Auction currentAuction = auctionRepository.findByAuctionId(auctionId)
                .orElseThrow(() -> AnotherArtException.type(AUCTION_NOT_FOUND));

        if (currentAuction.getEndDate().isBefore(LocalDateTime.now())) {
            throw AnotherArtException.type(CLOSED_AUCTION);
        } else if (currentAuction.getBidPrice() >= bidPrice) {
            throw AnotherArtException.type(BID_AMOUNT_NOT_ENOUGH);
        }

        User currentBidUser = userRepository.findById(userId)
                .orElseThrow(() -> AnotherArtException.type(USER_NOT_FOUND));

        if (currentBidUser.getAvailablePoint() < bidPrice) {
            throw AnotherArtException.type(POINT_NOT_ENOUGH);
        }

        Optional<User> previousBidUser = Optional.ofNullable(currentAuction.getUser());
        previousBidUser.ifPresent(users -> users.updateAvailablePoint(users.getAvailablePoint() + currentAuction.getBidPrice()));
        currentBidUser.updateAvailablePoint(currentBidUser.getAvailablePoint() - bidPrice);
        currentAuction.applyNewBid(currentBidUser, bidPrice);

        auctionHistoryRepository.save(AuctionHistory.createAuctionHistory(
                currentAuction,
                currentAuction.getArt(),
                currentBidUser,
                bidPrice
        ));
    }
}
