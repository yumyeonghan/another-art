package com.imagine.another_arts.domain.point.repository.custom;

import com.imagine.another_arts.domain.point.PointHistory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

import static com.imagine.another_arts.domain.point.QPointHistory.pointHistory;
import static com.imagine.another_arts.domain.user.QUser.user;

@RequiredArgsConstructor
public class PointHistoryQueryDSLRepositoryImpl implements PointHistoryQueryDSLRepository {

    private final JPAQueryFactory query;

    @Override
    public Long findLatestPointByUserId(Long userId) {
        return query
                .select(pointHistory.point)
                .from(pointHistory)
                .innerJoin(pointHistory.user, user)
                .where(userIdEq(userId))
                .orderBy(pointHistory.dealDate.desc())
                .fetchFirst();
    }

    @Override
    public List<PointHistory> findByUserIdInOrderByDealDateDesc(Collection<Long> userIdList) {
        return query
                .selectFrom(pointHistory)
                .innerJoin(pointHistory.user, user).fetchJoin()
                .where(userIdIn(userIdList))
                .orderBy(pointHistory.dealDate.desc())
                .fetch();
    }

    private BooleanExpression userIdEq(Long userId) {
        if (userId == null) {
            return null;
        }

        return user.id.eq(userId);
    }

    private BooleanExpression userIdIn(Collection<Long> userIdList) {
        if (userIdList == null) {
            return null;
        }

        return user.id.in(userIdList);
    }
}
