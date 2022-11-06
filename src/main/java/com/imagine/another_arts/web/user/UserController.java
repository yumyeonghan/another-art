package com.imagine.another_arts.web.user;

import com.imagine.another_arts.domain.login.dto.UserSessionDto;
import com.imagine.another_arts.domain.user.service.UserService;
import com.imagine.another_arts.domain.user.service.dto.response.MyPageUserResponse;
import com.imagine.another_arts.exception.AnotherArtException;
import com.imagine.another_arts.web.SessionFactory;
import com.imagine.another_arts.web.user.dto.request.DuplicateCheckRequest;
import com.imagine.another_arts.web.user.dto.request.UserEditRequest;
import com.imagine.another_arts.web.user.dto.request.UserJoinRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;

import static com.imagine.another_arts.exception.AnotherArtErrorCode.AUTHENTICATION_USER;
import static com.imagine.another_arts.exception.AnotherArtErrorCode.ILLEGAL_URL_REQUEST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Api(tags = "사용자 API")
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "회원가입 API", notes = "Form 데이터 정보들을 서버로 전달함에 따라 회원가입 진행")
    public ResponseEntity<Void> joinUser(@Valid @RequestBody UserJoinRequest joinRequest) {
        Long saveUserId = userService.saveUser(joinRequest.toEntity());
        return ResponseEntity
                .created(URI.create("/api/user/" + saveUserId))
                .build();
    }

    @PatchMapping("/user/{userId}")
    @ApiOperation(value = "회원수정 API", notes = "수정할 회원 정보들을 서버에 전달함에 따라 회원 수정 진행")
    public ResponseEntity<Void> editUser(@PathVariable Long userId, @RequestBody UserEditRequest editRequest) {
        userService.editUser(userId, editRequest.toServiceDto());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user/duplicate-check")
    @ApiOperation(value = "회원가입 중복 검사 API", notes = "[닉네임, 로그인 아이디, 전화번호, 이메일]에 대한 중복 검사 API")
    public ResponseEntity<Void> validateInformationInSignUp(@Valid @RequestBody DuplicateCheckRequest duplicateCheckRequest) {
        String resource = duplicateCheckRequest.getResource();
        String input = duplicateCheckRequest.getInput();

        switch (resource) {
            case "nickname":
                userService.hasDuplicateNickname(input);
                return ResponseEntity.noContent().build();

            case "loginId":
                userService.hasDuplicateLoginId(input);
                return ResponseEntity.noContent().build();

            case "phoneNumber":
                userService.hasDuplicatePhoneNumber(input);
                return ResponseEntity.noContent().build();

            case "email":
                userService.hasDuplicateEmail(input);
                return ResponseEntity.noContent().build();

            default:
                throw AnotherArtException.type(ILLEGAL_URL_REQUEST);
        }
    }

    @GetMapping("/user/{userId}")
    @ApiOperation(value = "마이페이지 사용자 정보 API", notes = "마이페이지에서 사용자에 관한 정보를 응답하는 API")
    public MyPageUserResponse getUserInformation(@PathVariable Long userId, HttpServletRequest request) {
        return userService.getUserInformation(request, userId);
    }

    @GetMapping("/session-check")
    @ApiOperation(value = "사용자 세션 체크 API", notes = "Request를 보낸 사용자가 Session을 보유하고 있는지 체크하는 API")
    public UserSessionDto sessionCheck(HttpServletRequest request) {
        UserSessionDto currentUserSession = (UserSessionDto) request.getSession(false).getAttribute(SessionFactory.ANOTHER_ART_SESSION_KEY);

        if (Objects.isNull(currentUserSession)) {
            throw AnotherArtException.type(AUTHENTICATION_USER);
        }

        return currentUserSession;
    }
}