package com.imagine.another_arts.domain.point.service;

import com.imagine.another_arts.domain.point.PointHistory;
import com.imagine.another_arts.domain.point.enums.DealType;
import com.imagine.another_arts.domain.point.repository.PointHistoryRepository;
import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointHistoryService {

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;


    @Transactional
    public void chargePoint(String loginId, Integer amount) {

        Users user = userRepository.findFirstByLoginId(loginId).get();

        PointHistory prePointHistory = pointHistoryRepository.findTopByUserOrderByDealDateDesc(user);

        PointHistory proPointHistory = PointHistory.insertPointHistory(user, DealType.CHARGE, amount, prePointHistory.getPoint() + amount);

        pointHistoryRepository.save(proPointHistory);

    }
}
