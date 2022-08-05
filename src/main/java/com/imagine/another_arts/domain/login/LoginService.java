package com.imagine.another_arts.domain.login;

import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    /**
     * @return null 은 로그인 실패
     */

    //로그인
    public Users login(String loginId, String loginPassword){
        Users findUser = userRepository.findByLoginId(loginId);
        if (findUser.getLoginPassword().equals(loginPassword)){
            return findUser;
        }

        return null;
    }

    /**
     * @return null 은 아이디 찾기 실패
     */

    //아이디 찾기
    public String findId(String name, String email){

        Users findUser = userRepository.findByEmail(email);
        if (findUser.getName().equals(name)){
            return findUser.getLoginId();
        }

        return null;
    }

    //비밀번호 재설정
    public void resetPw(String loginId, String changeLoginPassword){

        Users findUser = userRepository.findByLoginId(loginId);
        findUser.changePassword(changeLoginPassword);

    }
}
