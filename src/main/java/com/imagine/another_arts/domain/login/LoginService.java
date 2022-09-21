package com.imagine.another_arts.domain.login;

import com.imagine.another_arts.domain.login.dto.UserSessionDto;
import com.imagine.another_arts.domain.user.User;
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
    public UserSessionDto login(String loginId, String loginPassword) {
        User findUser = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("회원정보가 존재하지 않습니다"));

        isCorrectPassword(findUser, loginPassword);
        return new UserSessionDto(findUser);
    }

    private void isCorrectPassword(User findUser, String loginPassword) {
        if (!findUser.getLoginPassword().equals(loginPassword)) {
            throw new UserNotFoundException("비밀번호가 일치하지 않습니다");
        }
    }

    // [이름, 이메일]로 아이디 찾기
    public UserSessionDto findId(String name, String email){
        User findUser = userRepository.findByEmailAndName(email, name)
                .orElseThrow(() -> new UserNotFoundException("정보가 일치하지 않기 때문에 아이디를 찾을 수 없습니다"));

        return new UserSessionDto(findUser);
    }

    // [로그인 아이디, 이름, 이메일]로 비밀번호 찾기
    public UserSessionDto findPassword(String loginId, String name, String email){
        User findUser = userRepository.findByLoginIdAndNameAndEmail(loginId, name, email)
                .orElseThrow(() -> new UserNotFoundException("정보가 일치하지 않기 때문에 비밀번호를 찾을 수 없습니다"));

        return new UserSessionDto(findUser);
    }


    // 비밀번호 재설정
    @Transactional
    public void resetPassword(String loginId, String changeLoginPassword){
        User findUser = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("회원정보가 존재하지 않습니다"));
        findUser.changePassword(changeLoginPassword);
    }
}
