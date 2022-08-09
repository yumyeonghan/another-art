package com.imagine.another_arts.web.user;

import com.imagine.another_arts.domain.point.PointHistory;
import com.imagine.another_arts.domain.point.repository.PointHistoryRepository;
import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.dto.UserEditForm;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.domain.user.service.UserService;
import com.imagine.another_arts.web.user.dto.JoinForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final UserService userService;

    @PostMapping("/join")
    public Success join(@ModelAttribute JoinForm form) {

        Users user = Users.createUser(form.getName(), form.getNickname(), form.getLoginId(), form.getLoginPassword(),
                form.getSchoolName(), form.getPhoneNumber(), form.getAddress(), form.getBirth());

        PointHistory pointHistory = PointHistory.createPointHistory(user);

        userRepository.save(user);
        pointHistoryRepository.save(pointHistory);

        return new Success(true);
    }

    @PutMapping("/edit")
    public Success editUser(@ModelAttribute UserEditForm form) {

        userService.editUser(form);

        return new Success(true);
    }

    @Data
    @AllArgsConstructor
    static class Success {
        private boolean success;
    }

}
