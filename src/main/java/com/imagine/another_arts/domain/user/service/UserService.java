package com.imagine.another_arts.domain.user.service;

import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.IllegalUserModifyException;
import com.imagine.another_arts.exception.UserNotFoundException;
import com.imagine.another_arts.web.user.dto.UserEditRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void editUser(Long userId, UserEditRequest editForm) {
        Users findUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다"));

        if (StringUtils.hasText(editForm.getName())) {
            findUser.changeName(editForm.getName());
        }

        if (StringUtils.hasText(editForm.getNickname())) {
            if (validationChangeNickName(findUser.getId(), editForm.getNickname())) {
                findUser.changeNickname(editForm.getNickname());
            } else {
                throw new IllegalUserModifyException("이미 존재하는 닉네임입니다");
            }
        }

        if (StringUtils.hasText(editForm.getSchoolName())) {
            findUser.changeSchoolName(editForm.getSchoolName());
        }

        if (StringUtils.hasText(editForm.getPhoneNumber())) {
            if(validationChangePhoneNumber(findUser.getId(), editForm.getPhoneNumber())) {
                findUser.changePhoneNumber(editForm.getPhoneNumber());
            } else {
                throw new IllegalUserModifyException("이미 존재하는 전화번호 입니다");
            }
        }

        if (StringUtils.hasText(editForm.getAddress())) {
            findUser.changeAddress(editForm.getAddress());
        }
    }

    private boolean validationChangeNickName(Long userId, String changeNickname) {
        return userRepository.findByIdNotAndNickname(userId, changeNickname).isEmpty();
    }

    private boolean validationChangePhoneNumber(Long userId, String changePhoneNumber) {
        return userRepository.findByIdNotAndPhoneNumber(userId, changePhoneNumber).isEmpty();
    }
}