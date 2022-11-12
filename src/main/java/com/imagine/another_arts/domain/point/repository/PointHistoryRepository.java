package com.imagine.another_arts.domain.point.repository;

import com.imagine.another_arts.domain.point.PointHistory;
import com.imagine.another_arts.domain.point.repository.custom.PointHistoryQueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long>, PointHistoryQueryDslRepository {

}
