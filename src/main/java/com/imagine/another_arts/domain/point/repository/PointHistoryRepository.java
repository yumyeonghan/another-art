package com.imagine.another_arts.domain.point.repository;

import com.imagine.another_arts.domain.point.PointHistory;
import com.imagine.another_arts.domain.user.Users;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    PointHistory findTopByUserOrderByDealDateDesc(Users user);

}
