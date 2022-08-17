package com.imagine.another_arts.domain.user.repository;

import com.imagine.another_arts.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    // [로그인 아이디]로 User 찾기
    Optional<Users> findFirstByLoginId(String loginId);

    // [닉네임]로 User 찾기
    Optional<Users> findFirstByNickname(String nickname);

    // [이름, 이메일]로 User 찾기
    Optional<Users> findFirstByEmailAndName(String email, String name);

    // [로그인 아이디, 이름, 이메일]로 User 찾기
    Optional<Users> findFirstByLoginIdAndNameAndEmail(String loginId, String name, String email);

    // 동일 닉네임 여부 확인 (서버에서 1번 더 확인)
    Optional<Users> findByIdNotAndNickname(Long userId, String nickName);

    // 동일 전화번호 여부 확인 (서버에서 1번 더 확인)
    Optional<Users> findByIdNotAndPhoneNumber(Long userId, String phoneNumber);
}
