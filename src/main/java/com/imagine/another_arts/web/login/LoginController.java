package com.imagine.another_arts.web.login;

import com.imagine.another_arts.domain.login.LoginService;
import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.web.SessionConst;
import com.imagine.another_arts.web.login.dto.FindIdForm;
import com.imagine.another_arts.web.login.dto.FindPwForm;
import com.imagine.another_arts.web.login.dto.LoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "loginFail";
        }

        Users loginUser = loginService.login(loginForm.getLoginId(), loginForm.getLoginPassword());

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "loginFail";
        }

        HttpSession session = request.getSession();

        session.setAttribute(SessionConst.LOGIN_USER, loginUser);

        return "loginOk";

    }

    //로그아웃 기능
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "logoutOk";
    }

    //아이디 찾기
    @PostMapping(value = "/findId")
    @ResponseStatus(HttpStatus.OK)
    public String findId(@Valid @ModelAttribute FindIdForm findIdForm,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "findIdFail";
        }

        String findId = loginService.findId(findIdForm.getName(), findIdForm.getEmail());


        if (findId == null) {
            bindingResult.reject("findIdFail", "아이디가 존재하지 않습니다.");
            return "findIdFail";
        }

        return findId;
    }

    //비밀번호 찾기
    @PostMapping(value = "/findPw")
    @ResponseStatus(HttpStatus.OK)
    public String findPw(@Valid @ModelAttribute FindPwForm findPwForm,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "findPwFail";
        }

        String findId = loginService.findId(findPwForm.getName(), findPwForm.getEmail());

        if (findId != null && !findId.equals(findPwForm.getLoginId())) {
            bindingResult.reject("findIdFail", "아이디가 존재하지 않습니다.");
            return "findPwFail";
        }

        Users findUser = userRepository.findByLoginId(findId);


        return findUser.getLoginPassword();
    }

    //비밀번호 재설정
    @PostMapping("/resetPw")
    @ResponseStatus(HttpStatus.OK)
    public String resetPw(@RequestParam String loginId,
                          @RequestParam String changeLoginPassword) {

        loginService.resetPw(loginId, changeLoginPassword);

        return "resetPwOk";
    }
}
