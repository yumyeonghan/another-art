package com.imagine.another_arts.domain.auction;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id")
    private Long id;

    @Column(name = "bid_price", nullable = false)
    private Long bidPrice;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 경매에 참여하는 사람들 & 처음 경매 등록할때는 NULL 허용

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false, unique = true, updatable = false)
    private Art art;

    //==생성 메소드==//
    public static Auction createAuction(Long bidPrice, LocalDateTime startDate, LocalDateTime endDate, Art art) { // 경매 처음 등록할 때 사용
        Auction auction = new Auction();
        auction.bidPrice = bidPrice;
        auction.startDate = startDate;
        auction.endDate = endDate;
        auction.art = art;
        return auction;
    }

    //==관련 비즈니스 로직 작성 공간==//
    public void applyNewBid(User user, Long bidPrice){ // user의 새로운 비드 : bid 들어올 때 이 메소드 통해서 정보 변경
        this.user = user;
        this.bidPrice = bidPrice;
    }
}
