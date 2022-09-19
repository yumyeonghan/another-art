package com.imagine.another_arts.domain.purchase.service;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.art.enums.SaleStatus;
import com.imagine.another_arts.domain.art.enums.SaleType;
import com.imagine.another_arts.domain.art.repository.ArtRepository;
import com.imagine.another_arts.domain.auction.Auction;
import com.imagine.another_arts.domain.auction.repository.AuctionRepository;
import com.imagine.another_arts.domain.point.PointHistory;
import com.imagine.another_arts.domain.point.enums.DealType;
import com.imagine.another_arts.domain.point.repository.PointHistoryRepository;
import com.imagine.another_arts.domain.purchase.PurchaseHistory;
import com.imagine.another_arts.domain.purchase.repository.PurchaseHistoryRepository;
import com.imagine.another_arts.domain.purchase.service.dto.PurchaseArtRequestDto;
import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseHistoryService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final ArtRepository artRepository;

    // 일반 작품, 경매 작품 구매
    @Transactional
    public void savePurchaseHistory(PurchaseArtRequestDto purchaseArtRequestDto) {
        Art saleArt = artRepository.findFirstArtBy(purchaseArtRequestDto.getArtId())
                .orElseThrow(() -> new ArtNotFoundException("작품 정보가 존재하지 않습니다"));
        Users purchaseUser = userRepository.findById(purchaseArtRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다"));

        validateSaleableArt(saleArt);

        // SaleType = AUCTiON
        if(saleArt.getSaleType().equals(SaleType.AUCTION)) {
            Auction auction = auctionRepository.findFirstAuctionBy(saleArt.getId());
            validateQualifiedUser(purchaseUser, auction);

            saleArt.changeSaleStatus(SaleStatus.SOLD_OUT);
            pointHistoryRepository.save(PointHistory.insertPointHistory(
                    purchaseUser, DealType.USE,
                    auction.getBidPrice(),
                    pointHistoryRepository
                            .findTopByUserOrderByDealDateDesc(purchaseUser).getPoint() - auction.getBidPrice()));
            purchaseHistoryRepository.save(PurchaseHistory.createPurchaseHistoryByAuction(
                    purchaseUser, saleArt,
                    auction, auction.getBidPrice()
            ));
        }
        else { // SaleType = GENERAL
            validateSufficientPoint(saleArt, purchaseUser);

            purchaseUser.changeAvailablePoint(purchaseUser.getAvailablePoint() - saleArt.getInitPrice());
            saleArt.changeSaleStatus(SaleStatus.SOLD_OUT);
            pointHistoryRepository.save(PointHistory.insertPointHistory(
                    purchaseUser, DealType.USE,
                    saleArt.getInitPrice(),
                    pointHistoryRepository
                            .findTopByUserOrderByDealDateDesc(purchaseUser).getPoint() - saleArt.getInitPrice()));
            purchaseHistoryRepository.save(PurchaseHistory.createPurchaseHistoryByGeneral(
                    purchaseUser, saleArt,
                    saleArt.getInitPrice()));
        }
    }

    private void validateSufficientPoint(Art art, Users user) {
        if(user.getAvailablePoint() < art.getInitPrice()) {
            throw new PointNotFullException("포인트가 충분하지 않습니다다");
        }
    }

    private void validateQualifiedUser(Users user, Auction auction) {
        if(!auction.getUser().equals(user)) {
            throw new PurchaserNotQualifiedException("구매 자격이 없는 사용자입니다");
        }
    }

    private void validateSaleableArt(Art art) {
        if(art.getSaleStatus().equals(SaleStatus.SOLD_OUT)) {
            throw new ArtSoldOutException("이미 판매된 작품입니다");
        }
    }
}

