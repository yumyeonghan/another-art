package com.imagine.another_arts.domain.purchase;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.auction.Auction;
import com.imagine.another_arts.domain.purchase.enums.PurchaseCategory;
import com.imagine.another_arts.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "purchase_history")
@EntityListeners(AuditingEntityListener.class)
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", nullable = false, updatable = false)
    private Long price;

    @CreatedDate
    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "purchase_category", nullable = false, updatable = false, length = 10)
    private PurchaseCategory purchaseCategory; // GENERAL(일반 판매를 통한 구매), AUCTION(경매를 통한 낙찰) -> 이 값 그대로 insert

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false, updatable = false, unique = true)
    private Art art;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", unique = true) // 경매를 통한 구매일 경우 포함
    private Auction auction;

    // 생성 메소드 1 - 일반 구매 내역 //
    public static PurchaseHistory createPurchaseHistoryByGeneral(User user, Art art, Long price) {
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.user = user;
        purchaseHistory.art = art;
        purchaseHistory.price = price;
        purchaseHistory.purchaseCategory = PurchaseCategory.GENERAL;
        return purchaseHistory;
    }

    // 생성 메소드 2 - 경매 낙찰 내역 //
    public static PurchaseHistory createPurchaseHistoryByAuction(User user, Art art, Auction auction, Long price) {
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.user = user;
        purchaseHistory.art = art;
        purchaseHistory.auction = auction;
        purchaseHistory.price = price;
        purchaseHistory.purchaseCategory = PurchaseCategory.AUCTION;
        return purchaseHistory;
    }
}