package com.imagine.another_arts.domain.arthashtag.repository;

import com.imagine.another_arts.domain.arthashtag.ArtHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtHashtagRepository extends JpaRepository<ArtHashtag, Long> {

    @Query("SELECT ah" +
            " FROM ArtHashtag ah" +
            " JOIN FETCH ah.art" +
            " JOIN FETCH ah.hashtag" +
            " ORDER BY ah.art.name")
    List<ArtHashtag> findAllArtHashtag();
}
