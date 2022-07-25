package com.imagine.another_arts.domain.point;

import com.imagine.another_arts.domain.point.enums.DealType;
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
@Table(name = "point_history")
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "deal_type", nullable = false, length = 8)
    private DealType dealType; // JOIN(신규가입 - 포인트내역 instance 생성 = default), CHARGE(충전), REFUND(환불), USE(사용)

    @Column(name = "deal_amount", nullable = false)
    private Integer dealAmount; // default 0

    @Column(name = "point", nullable = false)
    private Integer point; // default 0

    @CreationTimestamp // PointHistory insert하면 자동 등록
    @Column(name = "deal_date")
    private LocalDateTime dealDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    // 생성 메소드 1 - 신규가입시 Instance Generate 용도 //
    public static PointHistory createPointHistory(Users user) {
        PointHistory pointHistory = new PointHistory();
        pointHistory.dealType = DealType.JOIN;
        pointHistory.dealAmount = 0;
        pointHistory.point = 0;
        pointHistory.user = user;
        return pointHistory;
    }

    // 생성 메소드 2 - 포인트 내역 누적 //
    public static PointHistory insertPointHistory(DealType dealType, Integer dealAmount, Integer point, Users user) {
        PointHistory pointHistory = new PointHistory();
        pointHistory.dealType = dealType;
        pointHistory.dealAmount = dealAmount;
        pointHistory.point = point;
        pointHistory.user = user;
        return pointHistory;
    }

    // 생성 메소드 3 - 테스트용 생성자 (6번 테스트) //
    public static PointHistory testPointHistory(Users user, Integer point) {
        PointHistory pointHistory = new PointHistory();
        pointHistory.dealType = DealType.JOIN;
        pointHistory.dealAmount = 0;
        pointHistory.point = point;
        pointHistory.user = user;
        return pointHistory;
    }

    //==테스트를 위한 toString()==//
    @Override
    public String toString() {
        return "\nPointHistory{" +
                "\n\tid=" + id +
                ", \n\tdealType=" + dealType +
                ", \n\tdealAmount=" + dealAmount +
                ", \n\tpoint=" + point +
                ", \n\tdealDate=" + dealDate +
                ", \n\tuser=" + user +
                "\n}";
    }
}
