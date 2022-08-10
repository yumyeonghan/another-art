package com.imagine.another_arts.web.login;

import com.imagine.another_arts.domain.login.LoginService;
import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.web.SessionConst;
import com.imagine.another_arts.web.login.dto.FindIdForm;
import com.imagine.another_arts.web.login.dto.FindPwForm;
import com.imagine.another_arts.web.login.dto.LoginForm;
import com.imagine.another_arts.web.login.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    private final UserRepository userRepository;

    //로그인 기능
    @PostMapping("/login")
    public LoginResponse login(@Valid @ModelAttribute LoginForm loginForm, HttpServletRequest request){


        Users loginUser = loginService.login(loginForm.getLoginId(), loginForm.getLoginPassword());

        HttpSession session = request.getSession();

        session.setAttribute(SessionConst.LOGIN_USER, loginUser);

        return new LoginResponse(true, loginUser.getName(), loginUser.getLoginId(), loginUser.getLoginPassword());

    }

    //로그아웃 기능
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "true";
    }

    //아이디 찾기
    @PostMapping(value = "/findId")
    public LoginResponse findId(@Valid @ModelAttribute FindIdForm findIdForm) {

        Users findUser = loginService.findId(findIdForm.getName(), findIdForm.getEmail());

        return new LoginResponse(true, findUser.getName(), findUser.getLoginId(), findUser.getLoginPassword());
    }

    //비밀번호 찾기
    @PostMapping(value = "/findPw")
    public LoginResponse findPw(@Valid @ModelAttribute FindPwForm findPwForm) {

        Users findId = loginService.findId(findPwForm.getName(), findPwForm.getEmail());

        Users findPw = loginService.findPw(findId.getLoginId(), findId.getName(), findId.getEmail());


        return new LoginResponse(true, findPw.getName(), findPw.getLoginId(), findPw.getLoginPassword());

    }

    //비밀번호 재설정
    @PostMapping("/resetPw")
    public String resetPw(@RequestParam String loginId,
                          @RequestParam String changeLoginPassword) {

        loginService.resetPw(loginId, changeLoginPassword);

        return "true";
    }
}
