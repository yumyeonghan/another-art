package com.imagine.another_arts.domain.user.service;

import com.imagine.another_arts.domain.point.PointHistory;
import com.imagine.another_arts.domain.point.repository.PointHistoryRepository;
import com.imagine.another_arts.domain.user.User;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.domain.user.service.dto.request.UserEditRequestDto;
import com.imagine.another_arts.domain.user.service.dto.request.UserJoinRequestDto;
import com.imagine.another_arts.exception.IllegalUserModifyException;
import com.imagine.another_arts.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public Long saveUser(UserJoinRequestDto userJoinRequestDto) {
        User user = User.createUser(
                userJoinRequestDto.getName(),
                userJoinRequestDto.getNickname(),
                userJoinRequestDto.getLoginId(),
                userJoinRequestDto.getLoginPassword(),
                userJoinRequestDto.getEmail(),
                userJoinRequestDto.getSchoolName(),
                userJoinRequestDto.getPhoneNumber(),
                userJoinRequestDto.getAddress(),
                userJoinRequestDto.getBirth()
        );
        User saveUser = userRepository.save(user);

        PointHistory pointHistory = PointHistory.createPointHistory(user);
        pointHistoryRepository.save(pointHistory);

        return saveUser.getId();
    }


    @Transactional
    public void editUser(Long userId, UserEditRequestDto editRequestDto) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다"));

        if (StringUtils.hasText(editRequestDto.getName())) {
            findUser.changeName(editRequestDto.getName());
        }

        if (StringUtils.hasText(editRequestDto.getNickname())) {
            if (validationChangeNickName(findUser.getId(), editRequestDto.getNickname())) {
                findUser.changeNickname(editRequestDto.getNickname());
            } else {
                throw new IllegalUserModifyException("이미 존재하는 닉네임입니다");
            }
        }

        if (StringUtils.hasText(editRequestDto.getSchoolName())) {
            findUser.changeSchoolName(editRequestDto.getSchoolName());
        }

        if (StringUtils.hasText(editRequestDto.getPhoneNumber())) {
            if(validationChangePhoneNumber(findUser.getId(), editRequestDto.getPhoneNumber())) {
                findUser.changePhoneNumber(editRequestDto.getPhoneNumber());
            } else {
                throw new IllegalUserModifyException("이미 존재하는 전화번호 입니다");
            }
        }

        if (StringUtils.hasText(editRequestDto.getAddress())) {
            findUser.changeAddress(editRequestDto.getAddress());
        }
    }

    private boolean validationChangeNickName(Long userId, String changeNickname) {
        return userRepository.findByIdNotAndNickname(userId, changeNickname).isEmpty();
    }

    private boolean validationChangePhoneNumber(Long userId, String changePhoneNumber) {
        return userRepository.findByIdNotAndPhoneNumber(userId, changePhoneNumber).isEmpty();
    }
}