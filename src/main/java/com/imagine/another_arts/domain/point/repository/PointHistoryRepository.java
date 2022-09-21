package com.imagine.another_arts.domain.point.repository;

import com.imagine.another_arts.domain.point.PointHistory;
import com.imagine.another_arts.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    //가장 최신 point 내역 조회
    PointHistory findTopByUserOrderByDealDateDesc(User user);

}
