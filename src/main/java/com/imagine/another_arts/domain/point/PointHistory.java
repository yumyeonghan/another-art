package com.imagine.another_arts.domain.point;

import com.imagine.another_arts.domain.point.enums.PointType;
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
@Table(name = "point_history")
@EntityListeners(AuditingEntityListener.class)
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "point_type", nullable = false, updatable = false, length = 8)
    private PointType pointType; // JOIN(신규가입 - 포인트내역 instance 생성 = default), CHARGE(충전), REFUND(환불), USE(사용)

    @Column(name = "deal_amount", nullable = false, updatable = false)
    private Long dealAmount; // default 0

    @Column(name = "point", nullable = false, updatable = false)
    private Long point; // default 0

    @CreatedDate
    @Column(name = "deal_date")
    private LocalDateTime dealDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    // 생성 메소드 1 - 신규가입시 Instance Generate 용도 //
    public static PointHistory createPointHistory(User user) {
        PointHistory pointHistory = new PointHistory();
        pointHistory.pointType = PointType.JOIN;
        pointHistory.dealAmount = 0L;
        pointHistory.point = 0L;
        pointHistory.user = user;
        return pointHistory;
    }

    // 생성 메소드 2 - 포인트 내역 누적 //
    public static PointHistory insertPointHistory(User user, PointType pointType, Long dealAmount, Long point) {
        PointHistory pointHistory = new PointHistory();
        pointHistory.pointType = pointType;
        pointHistory.dealAmount = dealAmount;
        pointHistory.point = point;
        pointHistory.user = user;
        return pointHistory;
    }

}
