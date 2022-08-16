package com.imagine.another_arts.domain.arthashtag;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.hashtag.Hashtag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "art_hashtag")
public class ArtHashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false)
    private Art art;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag;

    //==Art에 Hashtag 등록할 때 사용할 생성 메소드==//
    public static ArtHashtag insertArtHashtag(Art art, Hashtag hashtag) {
        ArtHashtag artHashtag = new ArtHashtag();
        artHashtag.art = art;
        artHashtag.hashtag = hashtag;
        return artHashtag;
    }
}
