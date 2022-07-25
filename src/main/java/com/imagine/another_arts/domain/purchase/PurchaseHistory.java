package com.imagine.another_arts.domain.purchase;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.auction.Auction;
import com.imagine.another_arts.domain.purchase.enums.PurchaseCategory;
import com.imagine.another_arts.domain.user.Users;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "purchase_history")
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_history_id")
    private Long id;

    @Column(name = "price", nullable = false)
    private Integer price;

    @CreationTimestamp
    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "purchase_category", nullable = false, length = 8)
    private PurchaseCategory purchaseCategory; // GENERAL(일반 판매를 통한 구매), AUCTION(경매를 통한 낙찰) -> 이 값 그대로 insert

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private Users user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false, unique = true)
    private Art art;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", unique = true) // 경매를 통한 구매일 경우 체크
    private Auction auction;


    // 생성 메소드 1 - 일반 구매 내역 //
    public static PurchaseHistory createPurchaseHistoryByGeneral(Users user, Art art, Integer price) {
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.user = user;
        purchaseHistory.art = art;
        purchaseHistory.price = price;
        purchaseHistory.purchaseCategory = PurchaseCategory.GENERAL;
        return purchaseHistory;
    }

    // 생성 메소드 2 - 경매 낙찰 내역 //
    public static PurchaseHistory createPurchaseHistoryByAuction(Users user, Art art, Auction auction, Integer price) {
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.user = user;
        purchaseHistory.art = art;
        purchaseHistory.auction = auction;
        purchaseHistory.price = price;
        purchaseHistory.purchaseCategory = PurchaseCategory.AUCTION;
        return purchaseHistory;
    }

    @Override
    public String toString() {
        return "\nPurchaseHistory{" +
                "\n\tid=" + id +
                ", \n\tprice=" + price +
                ", \n\tpurchaseDate=" + purchaseDate +
                ", \n\tpurchaseCategory=" + purchaseCategory +
                ", \n\tuser=" + user +
                "\n}";
    }
}

