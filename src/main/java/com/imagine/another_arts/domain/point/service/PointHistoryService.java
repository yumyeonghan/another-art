package com.imagine.another_arts.domain.point.service;

import com.imagine.another_arts.domain.point.PointHistory;
import com.imagine.another_arts.domain.point.enums.DealType;
import com.imagine.another_arts.domain.point.repository.PointHistoryRepository;
import com.imagine.another_arts.domain.user.User;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.PointNotFullException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointHistoryService {

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;


    @Transactional
    public void chargePoint(String loginId, Long amount) {

        User user = userRepository.findFirstByLoginId(loginId).get();

        PointHistory prePointHistory = pointHistoryRepository.findTopByUserOrderByDealDateDesc(user);

        PointHistory proPointHistory = PointHistory.insertPointHistory(user, DealType.CHARGE, amount, prePointHistory.getPoint() + amount);

        pointHistoryRepository.save(proPointHistory);

    }

    @Transactional
    public void refundPoint(String loginId, Long amount) {
        User user = userRepository.findFirstByLoginId(loginId).get();

        PointHistory prePointHistory = pointHistoryRepository.findTopByUserOrderByDealDateDesc(user);

        if (prePointHistory.getPoint() < amount) {
            throw new PointNotFullException("포인트가 부족하여 환불할 수 없습니다.");
        }

        PointHistory proPointHistory = PointHistory.insertPointHistory(user, DealType.REFUND, amount, prePointHistory.getPoint() - amount);

        pointHistoryRepository.save(proPointHistory);

    }
}
