package com.imagine.another_arts.domain.login;

import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    //로그인
    public Users login(String loginId, String loginPassword) {

        Optional<Users> findById = userRepository.findByLoginId(loginId);

        if (findById.isEmpty()){
            throw new UserNotFoundException("아이디가 존재하지 않습니다.");
        }

        return findById
                .filter(u->u.getLoginPassword().equals(loginPassword))
                .orElseThrow(()->new UserNotFoundException("아이디 또는 비밀번호가 올바르지 않습니다."));

    }

    //아이디 찾기
    public Users findId(String name, String email){

        Optional<Users> findByEmail = userRepository.findByEmail(email);

        if(findByEmail.isEmpty()){
            throw  new UserNotFoundException("이메일이 존재하지 않습니다.");
        }

        return findByEmail
                .filter(u->u.getName().equals(name))
                .orElseThrow(()->new UserNotFoundException("이메일 또는 이름이 올바르지 않습니다."));
    }

    //비밀번호 찾기
    public Users findPw(String loginId, String name, String email){

        Optional<Users> findById = userRepository.findByLoginId(loginId);

        if (findById.isEmpty()){
            throw new UserNotFoundException("아이디가 존재하지 않습니다.");
        }

        return findById
                .filter(u->u.getEmail().equals(email) && u.getName().equals(name))
                .orElseThrow(()->new UserNotFoundException("이메일 또는 이름이 올바르지 않습니다."));
    }


    //비밀번호 재설정
    @Transactional(readOnly = false)
    public void resetPw(String loginId, String changeLoginPassword){

        Optional<Users> findUser = userRepository.findByLoginId(loginId);
        Users user = findUser.get();
        user.changePassword(changeLoginPassword);


    }
}
