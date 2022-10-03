package com.imagine.another_arts.domain.login;

import com.imagine.another_arts.domain.login.dto.UserSessionDto;
import com.imagine.another_arts.domain.user.User;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.AnotherArtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.imagine.another_arts.exception.AnotherArtErrorCode.USER_NOT_FOUND;
import static com.imagine.another_arts.exception.AnotherArtErrorCode.WRONG_PASSWORD;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    // 로그인
    public UserSessionDto login(String loginId, String loginPassword) {
        User findUser = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> AnotherArtException.type(USER_NOT_FOUND));

        isCorrectPassword(findUser, loginPassword);
        return new UserSessionDto(findUser);
    }

    private void isCorrectPassword(User findUser, String loginPassword) {
        if (!findUser.getLoginPassword().equals(loginPassword)) {
            throw AnotherArtException.type(WRONG_PASSWORD);
        }
    }

    // [이름, 이메일]로 아이디 찾기
    public UserSessionDto findId(String name, String email){
        User findUser = userRepository.findByEmailAndName(email, name)
                .orElseThrow(() -> AnotherArtException.type(USER_NOT_FOUND));

        return new UserSessionDto(findUser);
    }

    // [로그인 아이디, 이름, 이메일]로 비밀번호 찾기
    public UserSessionDto findPassword(String loginId, String name, String email){
        User findUser = userRepository.findByLoginIdAndNameAndEmail(loginId, name, email)
                .orElseThrow(() -> AnotherArtException.type(USER_NOT_FOUND));

        return new UserSessionDto(findUser);
    }


    // 비밀번호 재설정
    @Transactional
    public void resetPassword(String loginId, String changeLoginPassword){
        User findUser = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> AnotherArtException.type(USER_NOT_FOUND));
        findUser.changePassword(changeLoginPassword);
    }
}
