package com.imagine.another_arts.domain.user.service;

import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.dto.UserEditForm;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public boolean editUser(UserEditForm editForm) {

        Users user = userRepository.findById(editForm.getId()).orElse(null);
        if (user == null) return false;

        user.changeName(editForm.getName());
        user.changeNickname(editForm.getNickname());
        user.changeSchoolName(editForm.getSchoolName());
        user.changePhoneNumber(editForm.getPhoneNumber());
        user.changeAddress(editForm.getAddress());
        user.changeBirth(editForm.getBirth());

        return true;
    }
}
