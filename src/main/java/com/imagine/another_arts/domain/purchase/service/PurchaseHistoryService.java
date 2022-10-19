package com.imagine.another_arts.domain.purchase.service;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.art.enums.SaleStatus;
import com.imagine.another_arts.domain.art.repository.ArtRepository;
import com.imagine.another_arts.domain.auction.Auction;
import com.imagine.another_arts.domain.auction.repository.AuctionRepository;
import com.imagine.another_arts.domain.point.PointHistory;
import com.imagine.another_arts.domain.point.enums.PointType;
import com.imagine.another_arts.domain.point.repository.PointHistoryRepository;
import com.imagine.another_arts.domain.purchase.PurchaseHistory;
import com.imagine.another_arts.domain.purchase.repository.PurchaseHistoryRepository;
import com.imagine.another_arts.domain.user.User;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.AnotherArtException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.imagine.another_arts.exception.AnotherArtErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseHistoryService {
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final ArtRepository artRepository;

    // 경매 작품 구매
    @Transactional
    public Long purchaseAuctionArt(Long auctionId, Long userId) {
        Auction auction = auctionRepository.findAuctionByAuctionId(auctionId)
                .orElseThrow(() -> AnotherArtException.type(AUCTION_NOT_FOUND));
        isAuctionPeriodFinished(auction);

        Art targetArt = auction.getArt();
        isAlreadySoldOut(targetArt);

        User purchaseUser = userRepository.findById(userId)
                .orElseThrow(() -> AnotherArtException.type(USER_NOT_FOUND));
        isQualifiedUser(auction.getUser(), purchaseUser);

        // PointHistory & PurchaseHistory Process
        return applyPurchaseHistoryAndPointHistory(targetArt, purchaseUser, auction);
    }

    // 일반 작품 구매
    @Transactional
    public Long purchaseGeneralArt(Long artId, Long userId) {
        Art targetArt = artRepository.findArtByArtId(artId)
                .orElseThrow(() -> AnotherArtException.type(ART_NOT_FOUND));
        isAlreadySoldOut(targetArt);

        User purchaseUser = userRepository.findById(userId)
                .orElseThrow(() -> AnotherArtException.type(USER_NOT_FOUND));
        hasSufficientPoint(purchaseUser, targetArt.getInitPrice());

        // PointHistory & PurchaseHistory Save Process
        return applyPurchaseHistoryAndPointHistory(targetArt, purchaseUser, null);
    }

    private Long applyPurchaseHistoryAndPointHistory(Art targetArt, User purchaseUser, @Nullable Auction auction) {
        // PurchaseHistory
        PurchaseHistory savePurchaseHistory = purchaseHistoryRepository.save(createPurchaseHistory(targetArt, purchaseUser, auction));
        targetArt.changeSaleStatus(SaleStatus.SOLD_OUT);

        // PointHistory
        savePointHistoryProcess(targetArt, targetArt.getUser(), purchaseUser, auction);

        return savePurchaseHistory.getId();
    }

    private PurchaseHistory createPurchaseHistory(Art targetArt, User purchaseUser, @Nullable Auction auction) {
        return (auction == null)
                ? PurchaseHistory.createPurchaseHistoryByGeneral(purchaseUser, targetArt, targetArt.getInitPrice())
                : PurchaseHistory.createPurchaseHistoryByAuction(purchaseUser, targetArt, auction, auction.getBidPrice());
    }

    private void savePointHistoryProcess(Art targetArt, User artOwner, User purchaseUser, @Nullable Auction auction) {
        // PointHistory Generate & SaveAll
        List<PointHistory> pointHistoryList = createPointHistory(targetArt, artOwner, purchaseUser, auction);
        pointHistoryRepository.saveAll(pointHistoryList);

        updateAvailablePoint(targetArt, artOwner, purchaseUser, auction);
    }

    private List<PointHistory> createPointHistory(Art targetArt, User artOwner, User purchaseUser, @Nullable Auction auction) {
        List<PointHistory> pointHistoryList = pointHistoryRepository.findByUserIdInOrderByDealDateDesc(List.of(purchaseUser.getId(), artOwner.getId()));
        Long artOwnerPoint = getLatestPointByUserId(pointHistoryList, artOwner.getId());
        Long purchaseUserPoint = getLatestPointByUserId(pointHistoryList, purchaseUser.getId());

        return (auction == null)
                ? List.of(
                PointHistory.insertPointHistory(artOwner, PointType.SOLD, targetArt.getInitPrice(), artOwnerPoint + targetArt.getInitPrice()),
                PointHistory.insertPointHistory(purchaseUser, PointType.USE, targetArt.getInitPrice(), purchaseUserPoint - targetArt.getInitPrice())
        )
                : List.of(
                PointHistory.insertPointHistory(artOwner, PointType.SOLD, auction.getBidPrice(), artOwnerPoint + auction.getBidPrice()),
                PointHistory.insertPointHistory(purchaseUser, PointType.USE, auction.getBidPrice(), purchaseUserPoint - auction.getBidPrice())
        );
    }

    private void updateAvailablePoint(Art targetArt, User artOwner, User purchaseUser, @Nullable Auction auction) {
        if (auction == null) {
            artOwner.updateAvailablePoint(artOwner.getAvailablePoint() + targetArt.getInitPrice());
            purchaseUser.updateAvailablePoint(purchaseUser.getAvailablePoint() - targetArt.getInitPrice());
        } else {
            artOwner.updateAvailablePoint(artOwner.getAvailablePoint() + auction.getBidPrice());
        }
    }

    private Long getLatestPointByUserId(List<PointHistory> pointHistoryList, Long userId) {
        return pointHistoryList.stream()
                .filter(pointHistory -> pointHistory.getUser().getId().equals(userId))
                .mapToLong(PointHistory::getPoint)
                .findFirst()
                .orElse(0L);
    }

    private void isAuctionPeriodFinished(Auction auction) {
        if (auction.getEndDate().isAfter(LocalDateTime.now())) {
            throw AnotherArtException.type(NOT_CLOSED_AUCTION);
        }
    }

    private void hasSufficientPoint(User user, Long purchasePrice) {
        if (user.getAvailablePoint() < purchasePrice) {
            throw AnotherArtException.type(POINT_NOT_ENOUGH);
        }
    }

    private void isQualifiedUser(User highestBidUser, User purchaseUser) {
        if (!highestBidUser.equals(purchaseUser)) {
            throw AnotherArtException.type(INELIGIBLE_USER_PURCHASE);
        }
    }

    private void isAlreadySoldOut(Art art) {
        if (art.getSaleStatus().equals(SaleStatus.SOLD_OUT)) {
            throw AnotherArtException.type(ART_SOLD_OUT);
        }
    }
}

