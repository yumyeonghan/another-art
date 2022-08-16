package com.imagine.another_arts.domain.login;

import com.imagine.another_arts.domain.login.dto.UserDto;
import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    // 로그인
    public UserDto login(String loginId, String loginPassword) {
        Users findUser = userRepository.findFirstByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("아이디에 대한 회원정보가 존재하지 않습니다"));

        validationUserLogin(findUser, loginPassword);
        return new UserDto(findUser);
    }

    private void validationUserLogin(Users findUser, String loginPassword) {
        if (!findUser.getLoginPassword().equals(loginPassword)) {
            throw new UserNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다");
        }
    }

    // [이름, 이메일]로 아이디 찾기
    public UserDto findId(String name, String email){
        Users findUser = userRepository.findFirstByEmailAndName(email, name)
                .orElseThrow(() -> new UserNotFoundException("이름, 이메일로 아이디를 찾을 수 없습니다"));

        return new UserDto(findUser);
    }

    // [로그인 아이디, 이름, 이메일]로 비밀번호 찾기
    public UserDto findPassword(String loginId, String name, String email){
        Users findUser = userRepository.findFirstByLoginIdAndNameAndEmail(loginId, name, email)
                .orElseThrow(() -> new UserNotFoundException("아이디, 이름, 이메일로 비밀번호를 찾을 수 없습니다"));

        return new UserDto(findUser);
    }


    // 비밀번호 재설정
    @Transactional
    public void resetPassword(String loginId, String changeLoginPassword){
        Users findUser = userRepository.findFirstByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("아이디에 대한 회원정보가 존재하지 않습니다"));
        findUser.changePassword(changeLoginPassword);
    }
}
