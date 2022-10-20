package com.imagine.another_arts.domain.user.repository.custom;

import com.imagine.another_arts.domain.user.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static com.imagine.another_arts.domain.user.QUser.user;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryDSLRepositoryImpl implements UserQueryDSLRepository {
    private final JPAQueryFactory query;

    @Override
    public User findUserWithSessionByLoginId(String loginId) {
        return query
                .select(user)
                .from(user)
                .where(loginIdEq(loginId))
                .fetchOne();
    }

    private BooleanExpression loginIdEq(String loginId) {
        if (loginId == null) {
            return null;
        }

        return user.loginId.eq(loginId);
    }
}
