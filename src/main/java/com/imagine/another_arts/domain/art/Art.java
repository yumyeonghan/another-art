package com.imagine.another_arts.domain.art;

import com.imagine.another_arts.domain.art.enums.SaleType;
import com.imagine.another_arts.domain.likeart.LikeArt;
import com.imagine.another_arts.domain.user.Users;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "art")
public class Art {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "art_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 160) // 설명 제한 50자 (추후에 변경 가능)
    private String description;

    @Column(name = "init_price", nullable = false)
    private Integer initPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_type", nullable = false, length = 8)
    private SaleType saleType; // GENERAL(일반 판매), AUCTION(경매를 통한 판매) -> 이 값 그대로 insert

    @CreationTimestamp // Art 저장하면 자동으로 등록 날짜 생성해서 insert
    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @Column(name = "upload_name", nullable = false, length = 100)
    private String uploadName; // user가 upload하는 파일명

    @Column(name = "storage_name", nullable = false, unique = true, length = 40) // 업로드 파일명 UUID로 변환 후 저장
    private String storageName; // 무조건 UUID 생성하고 replaceAll("-", "") 후 저장

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false) // 작품 등록하면 등록작가 변경 불가능 (FE에서 경고 알람 생성)
    private Users user;

    @OneToMany(mappedBy = "art")
    private List<LikeArt> likeArtList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "hashtag_list",
            joinColumns = @JoinColumn(name = "art_id")
    )
    @Column(name = "hashtag", length = 100)
    private List<String> hashtagList = new ArrayList<>();

    //==생성 메소드==//
    public static Art createArt(Users user, String name, String description, Integer initPrice, SaleType saleType,
                                String uploadName, String storageName) {
        Art art = new Art();
        art.user = user;
        art.name = name;
        art.description = description;
        art.initPrice = initPrice;
        art.saleType = saleType;
        art.uploadName = uploadName;
        art.storageName = storageName;
        return art;
    }

    //==관련 비즈니스 로직 작성 공간==//

    // 작품 이름 수정
    public void changeArtName(String name){
        this.name = name;
    }

    // 작품 설명 수정
    public void changeDescription(String description){
        this.description = description;
    }

    //==테스트를 위한 toString()==//
    @Override
    public String toString() {
        return "\nArt{" +
                "\n\tid=" + id +
                ", \n\tname='" + name + '\'' +
                ", \n\tdescription='" + description + '\'' +
                ", \n\tinitPrice=" + initPrice +
                ", \n\tsaleType=" + saleType +
                ", \n\tregisterDate=" + registerDate +
                ", \n\tuploadName='" + uploadName + '\'' +
                ", \n\tstorageName='" + storageName + '\'' +
                ", \n\tuser=" + user +
                "\n}";
    }
}
