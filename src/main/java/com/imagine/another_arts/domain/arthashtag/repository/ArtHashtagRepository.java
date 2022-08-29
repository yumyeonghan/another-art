package com.imagine.another_arts.domain.arthashtag.repository;

import com.imagine.another_arts.domain.arthashtag.ArtHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArtHashtagRepository extends JpaRepository<ArtHashtag, Long> {
    @Query("SELECT ah" +
            " FROM ArtHashtag ah" +
            " JOIN FETCH ah.art" +
            " JOIN FETCH ah.hashtag")
    List<ArtHashtag> findArtHashtagBy();

    @Query("SELECT ah" +
            " FROM ArtHashtag ah" +
            " JOIN FETCH ah.art" +
            " JOIN FETCH ah.hashtag" +
            " WHERE ah.art.id = :artId")
    List<ArtHashtag> findArtHashtagByArtId(@Param("artId") Long artId);

    List<ArtHashtag> findByArtId(Long artId);

    Optional<ArtHashtag> findByArtIdAndHashtagName(Long artId, String name);
}
