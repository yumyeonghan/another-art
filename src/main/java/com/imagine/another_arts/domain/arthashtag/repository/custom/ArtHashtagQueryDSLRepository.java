package com.imagine.another_arts.domain.arthashtag.repository.custom;

import com.imagine.another_arts.domain.arthashtag.ArtHashtag;

import java.util.Collection;
import java.util.List;

public interface ArtHashtagQueryDSLRepository {
    List<String> findHashtagListByArtId(Long artId);
    List<ArtHashtag> findArtHashtagList();
    Long deleteInBatchByArtIdAndHashtagIn(Long artId, Collection<String> hashtagName);
    Long deleteInBatchByArtId(Long artId);
}
