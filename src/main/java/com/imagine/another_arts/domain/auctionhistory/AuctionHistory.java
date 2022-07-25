package com.imagine.another_arts.domain.auctionhistory;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.auction.Auction;
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
@Table(name = "auction_history")
public class AuctionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bid_price", nullable = false)
    private int bidPrice;

    @CreationTimestamp
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
    public static AuctionHistory createAuctionHistory(Auction auction, Art art, Users user, int bidPrice) { // bid 들어오면 반드시 생성해서 insert
        AuctionHistory auctionHistory = new AuctionHistory();
        auctionHistory.auction = auction;
        auctionHistory.art = art;
        auctionHistory.user = user;
        auctionHistory.bidPrice = bidPrice;
        return auctionHistory;
    }

    //==테스트를 위한 toString()==//
    @Override
    public String toString() {
        return "\nAuctionHistory{" +
                "\n\tid=" + id +
                ", \n\tbidPrice=" + bidPrice +
                ", \n\tbidDate=" + bidDate +
                ", \n\tuser=" + user +
                "\n}";
    }
}
