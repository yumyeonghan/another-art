package com.imagine.another_arts.web.user;

import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.web.user.dto.JoinForm;
import com.imagine.another_arts.web.user.dto.UsersDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/users/join")
    public Result<UsersDto> join(@ModelAttribute JoinForm form) {
        Users user = Users.createUser(form.getName(), form.getNickname(), form.getLoginId(), form.getLoginPassword(),
                form.getSchoolName(), form.getPhoneNumber(), form.getAddress(), form.getBirth());

        Users savedUser = userRepository.save(user);
        UsersDto usersDto = new UsersDto(savedUser);

        return new Result<>(usersDto);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        T target;
    }
}
