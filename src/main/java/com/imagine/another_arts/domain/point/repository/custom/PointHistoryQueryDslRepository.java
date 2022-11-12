package com.imagine.another_arts.domain.point.repository.custom;

import com.imagine.another_arts.domain.point.PointHistory;

import java.util.Collection;
import java.util.List;

public interface PointHistoryQueryDslRepository {
    // [User]의 현재 포인트 보유 내역 조회 (비드 금액 포함)
    Long findLatestPointByUserId(Long userId);

    // [Collection<Long> userId]에 매칭되는 PointHistory 조회
    List<PointHistory> findByUserIdInOrderByDealDateDesc(Collection<Long> userIdList);
}
