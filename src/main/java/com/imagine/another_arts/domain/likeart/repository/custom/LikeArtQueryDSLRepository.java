package com.imagine.another_arts.domain.likeart.repository.custom;

import com.imagine.another_arts.domain.likeart.LikeArt;

import java.util.List;

public interface LikeArtQueryDSLRepository {
    List<LikeArt> findLikeArtList();

    Long getLikeArtCountByArtId(Long artId);
}
