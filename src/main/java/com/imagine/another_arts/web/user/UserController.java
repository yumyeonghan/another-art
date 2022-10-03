package com.imagine.another_arts.web.user;

import com.imagine.another_arts.domain.user.service.UserService;
import com.imagine.another_arts.domain.user.service.dto.response.MyPageUserResponse;
import com.imagine.another_arts.exception.AnotherArtException;
import com.imagine.another_arts.web.user.dto.request.DuplicateCheckRequest;
import com.imagine.another_arts.web.user.dto.request.UserEditRequest;
import com.imagine.another_arts.web.user.dto.request.UserJoinRequest;
import com.imagine.another_arts.web.user.dto.response.SimpleUserSuccessResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.imagine.another_arts.exception.AnotherArtErrorCode.ILLEGAL_URL_REQUEST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "회원가입 API", notes = "Form 데이터 정보들을 서버로 전달함에 따라 회원가입 진행")
    public ResponseEntity<SimpleUserSuccessResponse> joinUser(@Valid @RequestBody UserJoinRequest userJoinRequest) {
        Long saveUserId = userService.saveUser(userJoinRequest.toServiceDto());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Location", "/api/user/" + saveUserId);

        return new ResponseEntity<>(new SimpleUserSuccessResponse(saveUserId), headers, HttpStatus.CREATED);
    }

    @PatchMapping("/user/{userId}")
    @ApiOperation(value = "회원수정 API", notes = "수정할 회원 정보들을 서버에 전달함에 따라 회원 수정 진행")
    public ResponseEntity<Void> editUser(@PathVariable Long userId, @RequestBody UserEditRequest userEditRequest) {
        userService.editUser(userId, userEditRequest.toServiceDto());
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
    public MyPageUserResponse getUserInformation (@PathVariable Long userId, HttpServletRequest request) {
        return userService.getUserInformation(request, userId);
    }
}