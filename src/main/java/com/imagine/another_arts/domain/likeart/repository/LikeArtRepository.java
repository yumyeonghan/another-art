package com.imagine.another_arts.domain.likeart.repository;

import com.imagine.another_arts.domain.likeart.LikeArt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeArtRepository extends JpaRepository<LikeArt, Long> {
    @Query("SELECT la" +
            " FROM LikeArt la" +
            " JOIN FETCH la.user" +
            " JOIN FETCH la.art")
    List<LikeArt> findLikeArtsBy();
}
