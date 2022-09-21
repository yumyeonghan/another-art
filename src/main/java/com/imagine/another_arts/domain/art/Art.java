package com.imagine.another_arts.domain.art;

import com.imagine.another_arts.domain.art.enums.SaleStatus;
import com.imagine.another_arts.domain.art.enums.SaleType;
import com.imagine.another_arts.domain.arthashtag.ArtHashtag;
import com.imagine.another_arts.domain.likeart.LikeArt;
import com.imagine.another_arts.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Lob
    @Column(name = "description", nullable = false, length = 160, columnDefinition = "TEXT") // 글자 제한 X
    private String description;

    @Column(name = "init_price", nullable = false)
    private Long initPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_type", nullable = false, length = 8)
    private SaleType saleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_status", nullable = false, length = 8)
    private SaleStatus saleStatus;

    @CreatedDate
    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @Column(name = "upload_name", nullable = false, length = 100)
    private String uploadName;

    @Column(name = "storage_name", nullable = false, unique = true, length = 40)
    private String storageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "art") // QueryDSL
    private List<ArtHashtag> artHashtagList = new ArrayList<>();

    @OneToMany(mappedBy = "art") // QueryDSL
    private List<LikeArt> likeArtList = new ArrayList<>();

    //==생성 메소드==//
    public static Art createArt(User user, String name, String description, Long initPrice, String uploadName, String storageName) {
        Art art = new Art();
        art.user = user;
        art.name = name;
        art.description = description;
        art.initPrice = initPrice;
        art.saleStatus = SaleStatus.FOR_SALE;
        art.uploadName = uploadName;
        art.storageName = storageName;
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
    public void changeArtName(String name) {
        this.name = name;
    }

    // 작품 설명 수정
    public void changeDescription(String description) {
        this.description = description;
    }

    // 작품 판매 상태 수정
    public void changeSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }
}

