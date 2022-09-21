package com.imagine.another_arts.domain.likeart;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "like_art")
public class LikeArt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false)
    private Art art;

    //==생성 메소드==//
    public static LikeArt insertLikeArt(User user, Art art){
        LikeArt likeArt = new LikeArt();
        likeArt.user = user;
        likeArt.art = art;
        return likeArt;
    }
}
