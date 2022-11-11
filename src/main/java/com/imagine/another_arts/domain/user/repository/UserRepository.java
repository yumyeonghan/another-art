package com.imagine.another_arts.domain.user.repository;

import com.imagine.another_arts.domain.user.User;
import com.imagine.another_arts.domain.user.repository.custom.UserQueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserQueryDslRepository {
    // [로그인 아이디]로 User 찾기
    Optional<User> findByLoginId(String loginId);

    // [이름, 이메일]로 User 찾기
    Optional<User> findByEmailAndName(String email, String name);

    // [로그인 아이디, 이름, 이메일]로 User 찾기
    boolean existsByLoginIdAndNameAndEmail(String loginId, String name, String email);

    // 회원가입 - 닉네임 중복 체크
    boolean existsByNickname(String nickName);

    // 회원가입 - 아이디 중복 체크
    boolean existsByLoginId(String loginId);

    // 회원가입 - 전화번호 중복 체크
    boolean existsByPhoneNumber(String phoneNumber);

    // 회원가입 - 이메일 중복 체크
    boolean existsByEmail(String email);

    // 회원수정 - 닉네임 중복 체크
    boolean existsByIdNotAndNickname(Long userId, String nickName);

    // 회원수정 - 전화번호 중복 체크
    boolean existsByIdNotAndPhoneNumber(Long userId, String phoneNumber);
}
