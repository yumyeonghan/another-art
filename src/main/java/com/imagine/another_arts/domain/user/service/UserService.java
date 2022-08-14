package com.imagine.another_arts.domain.user.service;

import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.UserNotFoundException;
import com.imagine.another_arts.web.user.dto.UserEditForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void editUser(UserEditForm editForm) {
        Users user = userRepository.findById(editForm.getId())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 유저입니다"));

        user.changeName(editForm.getName());
        user.changeNickname(editForm.getNickname());
        user.changeSchoolName(editForm.getSchoolName());
        user.changePhoneNumber(editForm.getPhoneNumber());
        user.changeAddress(editForm.getAddress());
        user.changeBirth(editForm.getBirth());
    }
}