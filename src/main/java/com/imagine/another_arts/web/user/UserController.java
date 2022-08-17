package com.imagine.another_arts.web.user;

import com.imagine.another_arts.domain.point.PointHistory;
import com.imagine.another_arts.domain.point.repository.PointHistoryRepository;
import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.domain.user.service.UserService;
import com.imagine.another_arts.exception.IllegalUrlException;
import com.imagine.another_arts.exception.UserDuplicationException;
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

    @GetMapping("/user/duplication/{type}/{target}")
    @ApiOperation(value = "중복체크 API", notes = "중복체크 할 데이터를 서버에 전달함에 따라 중복여부 반환")
    public SimpleSucessResponse loginIdDuplicationCheck(@PathVariable String type, @PathVariable String target) {
        if (type.equals("loginId")) {
            userRepository.findFirstByLoginId(target).orElseThrow(() -> new UserDuplicationException("이미 존재하는 로그인 아이디 입니다."));
        } else if (type.equals("nickname")) {
            userRepository.findFirstByNickname(target).orElseThrow(() -> new UserDuplicationException("이미 존재하는 닉네임 입니다."));
        } else {
            throw new IllegalUrlException("올바르지 않은 url 입니다.");
        }

        return new SimpleSucessResponse(true);
    }
}
