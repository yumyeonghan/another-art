package com.imagine.another_arts.domain.arthashtag.repository;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.arthashtag.ArtHashtag;
import com.imagine.another_arts.domain.hashtag.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtHashtagRepository extends JpaRepository<ArtHashtag, Long> {
    @Query("SELECT ah" +
            " FROM ArtHashtag ah" +
            " JOIN FETCH ah.art" +
            " JOIN FETCH ah.hashtag")
    List<ArtHashtag> findArtHashtagBy();

    List<ArtHashtag> findByArtId(Long artId);

    Optional<ArtHashtag> findByArtIdAndHashtagName(Long artId, String name);

    boolean existsByArtAndHashtag(Art art, Hashtag hashtag);
}
