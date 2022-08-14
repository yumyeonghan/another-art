package com.imagine.another_arts.web.user;

import com.imagine.another_arts.domain.point.PointHistory;
import com.imagine.another_arts.domain.point.repository.PointHistoryRepository;
import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.domain.user.service.UserService;
import com.imagine.another_arts.web.user.dto.UserEditForm;
import com.imagine.another_arts.web.user.dto.UserJoinForm;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final UserService userService;

    @Data
    @AllArgsConstructor
    static class Success { // 간단한 boolean 응답 양식
        private boolean successResponse;
    }

    @PostMapping("/user/join")
    @ApiOperation(value = "회원가입 API", notes = "Form 데이터 정보들을 서버로 전달함에 따라 회원가입 진행")
    public Success joinUser(@Valid @ModelAttribute UserJoinForm userJoinForm) {
        Users user = Users.createUser(
                userJoinForm.getName(),
                userJoinForm.getNickname(),
                userJoinForm.getLoginId(),
                userJoinForm.getLoginPassword(),
                userJoinForm.getEmail(),
                userJoinForm.getSchoolName(),
                userJoinForm.getPhoneNumber(),
                userJoinForm.getAddress(),
                userJoinForm.getBirth()
        );
        PointHistory pointHistory = PointHistory.createPointHistory(user);

        userRepository.save(user);
        pointHistoryRepository.save(pointHistory);
        return new Success(true);
    }

    @PutMapping("/user/edit")
    @ApiOperation(value = "회원수정 API", notes = "수정할 회원 정보들을 서버에 전달함에 따라 회원 수정 진행")
    public Success editUser(@ModelAttribute UserEditForm editForm) {
        userService.editUser(editForm);
        return new Success(true);
    }
}
