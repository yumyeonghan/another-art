package com.imagine.another_arts.domain.auctionhistory;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.auction.Auction;
import com.imagine.another_arts.domain.user.Users;
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
@Table(name = "auction_history")
@EntityListeners(AuditingEntityListener.class)
public class AuctionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bid_price", nullable = false, updatable = false)
    private Integer bidPrice;

    @CreatedDate
    @Column(name = "bid_date")
    private LocalDateTime bidDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", updatable = false)
    private Art art;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", updatable = false)
    private Auction auction;

    //==생성 메소드==//
    public static AuctionHistory createAuctionHistory(Auction auction, Art art, Users user, Integer bidPrice) { // bid 들어오면 반드시 생성해서 insert
        AuctionHistory auctionHistory = new AuctionHistory();
        auctionHistory.auction = auction;
        auctionHistory.art = art;
        auctionHistory.user = user;
        auctionHistory.bidPrice = bidPrice;
        auction.getAuctionHistoryList().add(auctionHistory); // 양방향 매핑
        return auctionHistory;
    }
}
