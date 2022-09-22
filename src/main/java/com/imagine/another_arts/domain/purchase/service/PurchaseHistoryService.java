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
import com.imagine.another_arts.domain.purchase.service.dto.request.PurchaseAuctionArtRequestDto;
import com.imagine.another_arts.domain.purchase.service.dto.request.PurchaseGeneralArtRequestDto;
import com.imagine.another_arts.domain.user.User;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
    public Long purchaseAuctionArt(PurchaseAuctionArtRequestDto purchaseAuctionArtRequest) {
        Auction auction = auctionRepository.findAuctionByAuctionId(purchaseAuctionArtRequest.getAuctionId())
                .orElseThrow(() -> new AuctionNotFoundException("경매 정보가 존재하지 않습니다"));
        isAuctionInProgress(auction); // Validation

        Art targetArt = auction.getArt();
        isAlreadySoldOut(targetArt); // Validation

        User purchaseUser = userRepository.findById(purchaseAuctionArtRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다"));
        isQualifiedUser(auction.getUser(), purchaseUser); // Validation

        // PointHistory & PurchaseHistory Process
        return applyPurchaseHistoryAndPointHistory(targetArt, purchaseUser, auction);
    }

    // 일반 작품 구매
    @Transactional
    public Long purchaseGeneralArt(PurchaseGeneralArtRequestDto purchaseGeneralArtRequest) {
        Art targetArt = artRepository.findArtByArtId(purchaseGeneralArtRequest.getArtId())
                .orElseThrow(() -> new ArtNotFoundException("작품 정보가 존재하지 않습니다"));
        isAlreadySoldOut(targetArt); // Validation

        User purchaseUser = userRepository.findById(purchaseGeneralArtRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다"));
        hasSufficientPoint(purchaseUser, targetArt.getInitPrice()); // Validation

        // PointHistory & PurchaseHistory Process
        return applyPurchaseHistoryAndPointHistory(targetArt, purchaseUser, null);
    }

    @Transactional
    protected Long applyPurchaseHistoryAndPointHistory(Art targetArt, User purchaseUser, @Nullable Auction auction) {
        // PurchaseHistory
        PurchaseHistory savePurchaseHistory = purchaseHistoryRepository.save(createPurchaseHistory(targetArt, purchaseUser, auction));
        targetArt.changeSaleStatus(SaleStatus.SOLD_OUT);

        // PointHistory
        pointHistoryGenerateProcess(targetArt, targetArt.getUser(), purchaseUser, auction);

        return savePurchaseHistory.getId();
    }

    private PurchaseHistory createPurchaseHistory(Art targetArt, User purchaseUser, @Nullable Auction auction) {
        return (auction == null)
                ? PurchaseHistory.createPurchaseHistoryByGeneral(purchaseUser, targetArt, targetArt.getInitPrice())
                : PurchaseHistory.createPurchaseHistoryByAuction(purchaseUser, targetArt, auction, auction.getBidPrice());
    }

    @Transactional
    protected void pointHistoryGenerateProcess(Art targetArt, User artOwner, User purchaseUser, @Nullable Auction auction) {
        // PointHistory Generate & SaveAll
        List<PointHistory> pointHistoryList = createPointHistory(targetArt, artOwner, purchaseUser, auction);
        pointHistoryRepository.saveAll(pointHistoryList);

        // availablePoint Update
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

    @Transactional
    protected void updateAvailablePoint(Art targetArt, User artOwner, User purchaseUser, @Nullable Auction auction) {
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

    private void isAuctionInProgress(Auction auction) {
        if (auction.getEndDate().isAfter(LocalDateTime.now())) {
            throw new NotClosedAuctionException("종료되지 않은 경매 작품은 구매할 수 없습니다");
        }
    }

    private void hasSufficientPoint(User user, Long purchasePrice) {
        if (user.getAvailablePoint() < purchasePrice) {
            throw new PointNotEnoughException("포인트가 충분하지 않습니다");
        }
    }

    private void isQualifiedUser(User highestBidUser, User purchaseUser) {
        if (!highestBidUser.equals(purchaseUser)) {
            throw new UnQualifiedUserException("구매 자격이 없는 사용자입니다");
        }
    }

    private void isAlreadySoldOut(Art art) {
        if (art.getSaleStatus().equals(SaleStatus.SOLD_OUT)) {
            throw new ArtSoldOutException("이미 판매된 작품입니다");
        }
    }
}

