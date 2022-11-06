package com.imagine.another_arts.domain.likeart.repository.custom;

import com.imagine.another_arts.domain.likeart.LikeArt;

import java.util.List;

public interface LikeArtQueryDslRepository {
    List<LikeArt> findLikeArtList();
    Long getLikeArtCountByArtId(Long artId);
    Long deleteByArtAndUser(Long artId, Long userId);
}
