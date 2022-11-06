package com.imagine.another_arts.domain.auctionhistory.repository.custom;

import com.imagine.another_arts.domain.auctionhistory.AuctionHistory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.imagine.another_arts.domain.art.QArt.art;
import static com.imagine.another_arts.domain.auctionhistory.QAuctionHistory.auctionHistory;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionHistoryQueryDslRepositoryImpl implements AuctionHistoryQueryDslRepository {
    private final JPAQueryFactory query;

    @Override
    public List<AuctionHistory> findAllWithFetchArt() {
        return query
                .select(auctionHistory)
                .from(auctionHistory)
                .innerJoin(auctionHistory.art, art).fetchJoin()
                .fetch();
    }

    @Override
    public Long getAuctionHistoryCountByArtId(Long artId) {
        return query
                .select(auctionHistory.count())
                .from(auctionHistory)
                .innerJoin(auctionHistory.art, art)
                .where(artIdEq(artId))
                .fetchOne();
    }

    private BooleanExpression artIdEq(Long artId) {
        if (artId == null) {
            return null;
        }

        return art.id.eq(artId);
    }
}
