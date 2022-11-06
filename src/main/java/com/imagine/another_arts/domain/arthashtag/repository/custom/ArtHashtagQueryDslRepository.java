package com.imagine.another_arts.domain.arthashtag.repository.custom;

import com.imagine.another_arts.domain.arthashtag.ArtHashtag;

import java.util.Collection;
import java.util.List;

public interface ArtHashtagQueryDslRepository {
    List<String> findHashtagListByArtId(Long artId);
    List<ArtHashtag> findAllWithFetchArt();
    List<ArtHashtag> findAllByArtId(Long artId);
    Long deleteByArtIdAndHashtagNameIn(Long art_id, Collection<String> hashtagName);
}
