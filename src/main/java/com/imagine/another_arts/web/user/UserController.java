package com.imagine.another_arts.web.user;

import com.imagine.another_arts.domain.point.PointHistory;
import com.imagine.another_arts.domain.point.repository.PointHistoryRepository;
import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.domain.user.service.UserService;
import com.imagine.another_arts.web.SimpleSucessResponse;
import com.imagine.another_arts.web.user.dto.UserEditRequest;
import com.imagine.another_arts.web.user.dto.UserJoinRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final UserService userService;

    @PostMapping("/join/user")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "회원가입 API", notes = "Form 데이터 정보들을 서버로 전달함에 따라 회원가입 진행")
    public SimpleSucessResponse joinUser(@Valid @ModelAttribute UserJoinRequest userJoinRequest) {
        Users user = Users.createUser(
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
        PointHistory pointHistory = PointHistory.createPointHistory(user);

        userRepository.save(user);
        pointHistoryRepository.save(pointHistory);
        return new SimpleSucessResponse(true);
    }

    @PutMapping("/edit/user/{userId}")
    @ApiOperation(value = "회원수정 API", notes = "수정할 회원 정보들을 서버에 전달함에 따라 회원 수정 진행")
    public SimpleSucessResponse editUser(
            @PathVariable Long userId,
            @ModelAttribute UserEditRequest editForm
    ) {
        userService.editUser(userId, editForm);
        return new SimpleSucessResponse(true);
    }
}
