package com.imagine.another_arts.domain.auction.service;

import com.imagine.another_arts.domain.art.repository.ArtRepository;
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
import java.util.Objects;
import java.util.Optional;

import static com.imagine.another_arts.exception.AnotherArtErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionService {
    private final AuctionHistoryRepository auctionHistoryRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final ArtRepository artRepository;

    @Transactional
    public synchronized void updateHighestBidDetails(Long auctionId, Long userId, Long newBidPrice) {
        Auction currentAuction = auctionRepository.findByAuctionId(auctionId)
                .orElseThrow(() -> AnotherArtException.type(AUCTION_NOT_FOUND));
        User currentBidUser = userRepository.findById(userId)
                .orElseThrow(() -> AnotherArtException.type(USER_NOT_FOUND));

        isUserEqualsArtOwner( // 비드한 사용자가 경매 작품 소유자인지 여부 체크
                currentBidUser,
                artRepository.findByArtId(currentAuction.getArt().getId())
                        .orElseThrow(() -> AnotherArtException.type(ART_NOT_FOUND))
                        .getUser()
        );
        validateAuctionState(currentAuction.getEndDate()); // 현재 경매 상태 체크 (판매 중 / 이미 팔림)
        validateBidPriceIsAcceptable(currentAuction.getBidPrice(), newBidPrice); // 비드 가격 체크
        validateUserCurrentAvailablePointToParticipateAuction(currentBidUser.getAvailablePoint(), newBidPrice); // 현재 사용자의 포인트 충분 유무 체크

        Optional<User> previousBidUser = Optional.ofNullable(currentAuction.getUser());
        previousBidUser.ifPresent(users -> users.updateAvailablePoint(users.getAvailablePoint() + currentAuction.getBidPrice()));
        currentBidUser.updateAvailablePoint(currentBidUser.getAvailablePoint() - newBidPrice);
        currentAuction.applyNewBid(currentBidUser, newBidPrice);

        auctionHistoryRepository.save(AuctionHistory.createAuctionHistory(currentAuction, currentAuction.getArt(), currentBidUser, newBidPrice));
    }

    private void isUserEqualsArtOwner(User currentUser, User artOwner) {
        if (Objects.equals(currentUser.getId(), artOwner.getId())) {
            throw AnotherArtException.type(ILLEGAL_PURCHASE_ACCESS);
        }
    }

    private void validateAuctionState(LocalDateTime auctionEndTime) {
        if (auctionEndTime.isBefore(LocalDateTime.now())) {
            throw AnotherArtException.type(CLOSED_AUCTION);
        }
    }

    private void validateBidPriceIsAcceptable(Long currentBidPrice, Long newBidPrice) {
        if (currentBidPrice >= newBidPrice) {
            throw AnotherArtException.type(BID_AMOUNT_NOT_ENOUGH);
        }
    }

    private void validateUserCurrentAvailablePointToParticipateAuction(Long currentAvailablePoint, Long newBidPrice) {
        if (currentAvailablePoint < newBidPrice) {
            throw AnotherArtException.type(POINT_NOT_ENOUGH);
        }
    }
}
