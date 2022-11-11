package com.imagine.another_arts.domain.login;

import com.imagine.another_arts.common.crypto.PasswordEncoder;
import com.imagine.another_arts.domain.login.dto.UserSessionDto;
import com.imagine.another_arts.domain.user.User;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.exception.AnotherArtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.imagine.another_arts.exception.AnotherArtErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 로그인
    public UserSessionDto login(String loginId, String loginPassword) {
        User findUser = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> AnotherArtException.type(USER_NOT_FOUND));
        isCorrectPassword(findUser, loginPassword);
        return new UserSessionDto(findUser);
    }

    private void isCorrectPassword(User findUser, String loginPassword) {
        if (!passwordEncoder.isMatch(loginPassword, findUser.getLoginPassword())) {
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
    public void userAuthenticationForFindPassword(String loginId, String name, String email){
        if (!userRepository.existsByLoginIdAndNameAndEmail(loginId, name, email)) {
            throw AnotherArtException.type(USER_NOT_FOUND);
        }
    }

    // 비밀번호 재설정
    @Transactional
    public void resetPassword(String loginId, String changeLoginPassword){
        User findUser = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> AnotherArtException.type(USER_NOT_FOUND));
        isSamePasswordAsBefore(findUser.getLoginPassword(), changeLoginPassword);
        findUser.changePassword(passwordEncoder.encryptPassword(changeLoginPassword));
    }

    private void isSamePasswordAsBefore(String originPassword, String changePassword) {
        if (passwordEncoder.isMatch(changePassword, originPassword)) {
            throw AnotherArtException.type(SAME_PASSWORD_AS_BEFORE);
        }
    }
}
