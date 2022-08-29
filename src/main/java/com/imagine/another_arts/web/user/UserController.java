package com.imagine.another_arts.web.user;

import com.imagine.another_arts.domain.user.service.UserService;
import com.imagine.another_arts.web.user.dto.request.UserEditRequest;
import com.imagine.another_arts.web.user.dto.request.UserJoinRequest;
import com.imagine.another_arts.web.user.dto.response.SimpleUserSuccessResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    @ApiOperation(value = "회원가입 API", notes = "Form 데이터 정보들을 서버로 전달함에 따라 회원가입 진행")
    public ResponseEntity<SimpleUserSuccessResponse> joinUser(@Valid @ModelAttribute UserJoinRequest userJoinRequest) {
        Long saveUserId = userService.saveUser(userJoinRequest.toServiceDto());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Location", "/api/user/" + saveUserId);

        return new ResponseEntity<>(new SimpleUserSuccessResponse(saveUserId), headers, HttpStatus.CREATED);
    }

    @PatchMapping("/user/{userId}")
    @ApiOperation(value = "회원수정 API", notes = "수정할 회원 정보들을 서버에 전달함에 따라 회원 수정 진행")
    public ResponseEntity<Void> editUser(
            @PathVariable Long userId,
            @ModelAttribute UserEditRequest userEditRequest
    ) {
        userService.editUser(userId, userEditRequest.toServiceDto());
        return ResponseEntity.noContent().build();
    }
}
