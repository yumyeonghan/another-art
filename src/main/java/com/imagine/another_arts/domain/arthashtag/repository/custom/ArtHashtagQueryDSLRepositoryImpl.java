package com.imagine.another_arts.domain.arthashtag.repository.custom;

import com.imagine.another_arts.domain.arthashtag.ArtHashtag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

import static com.imagine.another_arts.domain.art.QArt.art;
import static com.imagine.another_arts.domain.arthashtag.QArtHashtag.artHashtag;

@RequiredArgsConstructor
public class ArtHashtagQueryDSLRepositoryImpl implements ArtHashtagQueryDSLRepository {
    private final JPAQueryFactory query;

    @Override
    public List<String> findHashtagListByArtId(Long artId) {
        return query
                .select(artHashtag.name)
                .from(artHashtag)
                .innerJoin(artHashtag.art, art)
                .where(artIdEq(artId))
                .fetch();
    }

    @Override
    public List<ArtHashtag> findArtHashtagList() {
        return query
                .select(artHashtag)
                .from(artHashtag)
                .innerJoin(artHashtag.art, art).fetchJoin()
                .fetch();
    }

    @Override
    public List<ArtHashtag> findArtHashtagListByArtId(Long artId) {
        return query
                .select(artHashtag)
                .from(artHashtag)
                .innerJoin(artHashtag.art, art).fetchJoin()
                .where(artIdEq(artId))
                .fetch();
    }

    @Override
    public Long deleteByArtIdAndHashtagNameIn(Long artId, Collection<String> hashtagName) {
        return query
                .delete(artHashtag)
                .where(artIdEq(artId), hashtagNameIn(hashtagName))
                .execute();
    }

    private BooleanExpression artIdEq(Long artId) {
        if (artId == null) {
            return null;
        }

        return art.id.eq(artId);
    }

    private BooleanExpression hashtagNameIn(Collection<String> hashtagName) {
        if (hashtagName == null) {
            return null;
        }

        return artHashtag.name.in(hashtagName);
    }
}
