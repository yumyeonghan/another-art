package com.imagine.another_arts.domain.point.service;

import com.imagine.another_arts.domain.point.PointHistory;
import com.imagine.another_arts.domain.point.enums.PointType;
import com.imagine.another_arts.domain.point.repository.PointHistoryRepository;
import com.imagine.another_arts.domain.user.User;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.AnotherArtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.imagine.another_arts.exception.AnotherArtErrorCode.POINT_NOT_ENOUGH;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointHistoryService {
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public void chargePoint(String loginId, Long dealAmount) {
        User findUser = userRepository.findUserWithSessionByLoginId(loginId);
        Long currentPoint = pointHistoryRepository.findLatestPointByUserId(findUser.getId());

        pointHistoryRepository.save(PointHistory.insertPointHistory(findUser, PointType.CHARGE, dealAmount, currentPoint + dealAmount));
        findUser.updateAvailablePoint(findUser.getAvailablePoint() + dealAmount); // 사용 가능 포인트 Update(+)
    }

    @Transactional
    public void refundPoint(String loginId, Long dealAmount) {
        User findUser = userRepository.findUserWithSessionByLoginId(loginId);
        Long currentPoint = pointHistoryRepository.findLatestPointByUserId(findUser.getId());

        if (findUser.getAvailablePoint() < dealAmount) {
            throw AnotherArtException.type(POINT_NOT_ENOUGH);
        }

        pointHistoryRepository.save(PointHistory.insertPointHistory(findUser, PointType.REFUND, dealAmount, currentPoint - dealAmount));
        findUser.updateAvailablePoint(findUser.getAvailablePoint() - dealAmount); // 사용 가능 포인트 Update(-)
    }
}
