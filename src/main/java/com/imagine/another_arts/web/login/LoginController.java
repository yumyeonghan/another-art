package com.imagine.another_arts.web.login;

import com.imagine.another_arts.domain.login.LoginService;
import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.web.SimpleSucessResponse;
import com.imagine.another_arts.web.login.dto.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.imagine.another_arts.web.SessionConst.LOGIN_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    @ApiOperation(value = "로그인 API", notes = "아이디, 비밀번호를 통해서 로그인을 진행")
    public LoginResponse userLogin(
            @Valid @ModelAttribute LoginRequest loginRequest,
            HttpServletRequest request
    ){
        Users loginUser = loginService.login(loginRequest.getLoginId(), loginRequest.getLoginPassword());
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_USER, loginUser);

        return new LoginResponse(
                true,
                loginUser.getId(),
                loginUser.getName(),
                loginUser.getNickname(),
                loginUser.getLoginId()
        );
    }

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃 API", notes = "사용자 로그아웃 (세션 만료)")
    public SimpleSucessResponse userLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return new SimpleSucessResponse(true);
    }

    @PostMapping(value = "/find/id")
    @ApiOperation(value = "아이디 찾기 API", notes = "이름, 이메일을 통해서 사용자의 아이디 찾기")
    public FindIdResponse findId(@Valid @ModelAttribute FindIdRequest findIdRequest) {
        Users findUser = loginService.findId(findIdRequest.getName(), findIdRequest.getEmail());

        return new FindIdResponse(
                true,
                findUser.getId(),
                findUser.getName(),
                findUser.getNickname(),
                findUser.getLoginId()
        );
    }

    @PostMapping(value = "/find/password")
    @ApiOperation(value = "비밀번호 찾기 API", notes = "로그인 아이디, 이름, 이메일을 통해서 사용자의 비밀번호 찾기")
    public FindPasswordResponse findPassword(@Valid @ModelAttribute FindPasswordRequest findPasswordRequest) {
        Users findUser = loginService.findPassword(
                findPasswordRequest.getLoginId(),
                findPasswordRequest.getName(),
                findPasswordRequest.getEmail()
        );

        return new FindPasswordResponse(
                true,
                findUser.getId(),
                findUser.getName(),
                findUser.getNickname(),
                findUser.getLoginId(),
                findUser.getLoginPassword()
        );
    }

    @PostMapping("/reset/password")
    @ApiOperation(value = "비밀번호 재설정 API", notes = "로그인 아이디를 통해서 사용자를 찾고, 비밀번호를 재설정")
    public SimpleSucessResponse resetPassword(@Valid @ModelAttribute ResetPasswordRequest resetPasswordRequest) {
        loginService.resetPassword(resetPasswordRequest.getLoginId(), resetPasswordRequest.getChangePassword());
        return new SimpleSucessResponse(true);
    }
}
