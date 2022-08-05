package com.imagine.another_arts.domain.login;

import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import org.apache.catalina.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginService loginService;

    @Test
    public void 로그인(){


        Users findUser = loginService.login("1", "1");
        assertThat(findUser.getLoginId()).isEqualTo("1");
        assertThat(findUser.getLoginPassword()).isEqualTo("1");


    }

    @Test
    public void 아이디찾기(){

        String findId = loginService.findId("1", "1");
        assertThat(findId).isEqualTo("1");
    }

    @Test
    public void 비밀번호재설정(){

        loginService.resetPw("1","2");
        Users findUser = userRepository.findByLoginId("1");
        assertThat(findUser.getLoginPassword()).isEqualTo("2");

    }

}