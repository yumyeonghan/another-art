package com.imagine.another_arts.domain.point.repository;

import com.imagine.another_arts.domain.point.PointHistory;
import com.imagine.another_arts.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    Optional<PointHistory> findByUser(Users users);

}
