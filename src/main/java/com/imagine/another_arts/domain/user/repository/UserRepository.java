package com.imagine.another_arts.domain.user.repository;

import com.imagine.another_arts.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    // [로그인 아이디]로 User 찾기
    Optional<Users> findFirstByLoginId(String loginId);

    // [이름, 이메일]로 User 찾기
    Optional<Users> findFirstByEmailAndName(String email, String name);

    // [로그인 아이디, 이름, 이메일]로 User 찾기
    Optional<Users> findFirstByLoginIdAndNameAndEmail(String loginId, String name, String email);
}
