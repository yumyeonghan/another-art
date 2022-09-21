package com.imagine.another_arts.domain.user.service;

import com.imagine.another_arts.domain.point.PointHistory;
import com.imagine.another_arts.domain.point.repository.PointHistoryRepository;
import com.imagine.another_arts.domain.user.User;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.domain.user.service.dto.request.UserEditRequestDto;
import com.imagine.another_arts.domain.user.service.dto.request.UserJoinRequestDto;
import com.imagine.another_arts.exception.DuplicateUserInfoException;
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
    public Long saveUser(UserJoinRequestDto userJoinRequest) {
        User user = User.createUser(
                userJoinRequest.getName(),
                userJoinRequest.getNickname(),
                userJoinRequest.getLoginId(),
                userJoinRequest.getLoginPassword(),
                userJoinRequest.getEmail(),
                userJoinRequest.getSchoolName(),
                userJoinRequest.getPhoneNumber(),
                userJoinRequest.getAddress(),
                userJoinRequest.getBirth()
        );
        User saveUser = userRepository.save(user);
        pointHistoryRepository.save(PointHistory.createPointHistory(user));

        return saveUser.getId();
    }


    @Transactional
    public void editUser(Long userId, UserEditRequestDto userEditRequest) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다"));

        if (StringUtils.hasText(userEditRequest.getName())) {
            findUser.changeName(userEditRequest.getName());
        }

        if (StringUtils.hasText(userEditRequest.getNickname())) {
            checkDuplicateNicknameInModification(findUser.getId(), userEditRequest.getNickname());
            findUser.changeNickname(userEditRequest.getNickname());
        }

        if (StringUtils.hasText(userEditRequest.getSchoolName())) {
            findUser.changeSchoolName(userEditRequest.getSchoolName());
        }

        if (StringUtils.hasText(userEditRequest.getPhoneNumber())) {
            checkDuplicatePhoneNumberInModification(findUser.getId(), userEditRequest.getPhoneNumber());
            findUser.changePhoneNumber(userEditRequest.getPhoneNumber());
        }

        if (StringUtils.hasText(userEditRequest.getAddress())) {
            findUser.changeAddress(userEditRequest.getAddress());
        }
    }

    public void checkDuplicateNicknameInModification(Long userId, String changeNickname) {
        if (userRepository.existsByIdNotAndNickname(userId, changeNickname)) {
            throw new IllegalUserModifyException("이미 존재하는 닉네임입니다");
        }
    }

    public void checkDuplicatePhoneNumberInModification(Long userId, String changePhoneNumber) {
        if (userRepository.existsByIdNotAndPhoneNumber(userId, changePhoneNumber)) {
            throw new IllegalUserModifyException("이미 존재하는 전화번호 입니다");
        }
    }

    public void hasDuplicateNickname(String nickName) {
        if (userRepository.existsByNickname(nickName)) {
            throw new DuplicateUserInfoException("이미 존재하는 닉네임입니다");
        }
    }

    public void hasDuplicateLoginId(String loginId) {
        if (userRepository.existsByLoginId(loginId)) {
            throw new DuplicateUserInfoException("이미 존재하는 아이디입니다");
        }
    }

    public void hasDuplicatePhoneNumber(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DuplicateUserInfoException("이미 존재하는 전화번호입니다");
        }
    }

    public void hasDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateUserInfoException("이미 존재하는 이메일입니다");
        }
    }
}