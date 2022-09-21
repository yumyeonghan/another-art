package com.imagine.another_arts.domain.arthashtag;

import com.imagine.another_arts.domain.art.Art;
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

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false)
    private Art art;

    //==생성 메소드==//
    public static ArtHashtag insertArtHashtag(Art art, String name) {
        ArtHashtag artHashtag = new ArtHashtag();
        artHashtag.art = art;
        artHashtag.name = name;
        art.getArtHashtagList().add(artHashtag);
        return artHashtag;
    }
}