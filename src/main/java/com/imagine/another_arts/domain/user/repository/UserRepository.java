package com.imagine.another_arts.domain.user.repository;

import com.imagine.another_arts.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // [로그인 아이디]로 User 찾기
    Optional<User> findFirstByLoginId(String loginId);

    // [이름, 이메일]로 User 찾기
    Optional<User> findFirstByEmailAndName(String email, String name);

    // [로그인 아이디, 이름, 이메일]로 User 찾기
    Optional<User> findFirstByLoginIdAndNameAndEmail(String loginId, String name, String email);

    // 동일 닉네임 여부 확인 (서버에서 1번 더 확인)
    Optional<User> findByIdNotAndNickname(Long userId, String nickName);

    // 동일 전화번호 여부 확인 (서버에서 1번 더 확인)
    Optional<User> findByIdNotAndPhoneNumber(Long userId, String phoneNumber);
}
