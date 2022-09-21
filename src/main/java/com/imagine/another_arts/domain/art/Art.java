package com.imagine.another_arts.domain.art;

import com.imagine.another_arts.domain.art.enums.SaleStatus;
import com.imagine.another_arts.domain.art.enums.SaleType;
import com.imagine.another_arts.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "art")
@EntityListeners(AuditingEntityListener.class)
public class Art {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 160) // 설명 제한 50자 (추후에 변경 가능)
    private String description;

    @Column(name = "init_price", nullable = false)
    private Long initPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_type", nullable = false, length = 8)
    private SaleType saleType; // GENERAL(일반 판매), AUCTION(경매를 통한 판매) -> 이 값 그대로 insert

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_status", nullable = false, length = 8)
    private SaleStatus saleStatus; // FOR_SALE(판매 중), SOLD_OUT(판매 완료)

    @CreatedDate
    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @Column(name = "upload_name", nullable = false, length = 100)
    private String uploadName; // user가 upload하는 파일명

    @Column(name = "storage_name", nullable = false, unique = true, length = 40) // 업로드 파일명 UUID로 변환 후 저장
    private String storageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false) // 작품 등록하면 등록작가 변경 불가능 (FE에서 경고 알람 생성)
    private User user;

    //==생성 메소드==//
    public static Art createArt(User user, String name, String description, Long initPrice, String uploadName, String storageName) {
        Art art = new Art();
        art.user = user;
        art.name = name;
        art.description = description;
        art.initPrice = initPrice;
        art.uploadName = uploadName;
        art.storageName = storageName;
        art.saleStatus = SaleStatus.FOR_SALE;
        return art;
    }

    //==관련 비즈니스 로직 작성 공간==//

    // 작품 타입 설정
    public void chooseSaleType(String saleType) {
        if (saleType.equals("auction")) {
            this.saleType = SaleType.AUCTION;
        } else {
            this.saleType = SaleType.GENERAL;
        }
    }

    // 작품 이름 수정
    public void changeArtName(String name){
        this.name = name;
    }

    // 작품 설명 수정
    public void changeDescription(String description){
        this.description = description;
    }

    // 작품 판매 상태 수정
    public void changeSaleStatus(SaleStatus saleStatus) { this.saleStatus = saleStatus; }
}

