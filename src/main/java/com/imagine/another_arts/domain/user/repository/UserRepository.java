package com.imagine.another_arts.domain.user.repository;

import com.imagine.another_arts.domain.user.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, Long> {

    //loginId 로 User 찾기
    @Query(value = "SELECT u FROM Users u WHERE u.loginId =:loginId")
    Users findByLoginId(@Param("loginId") String loginId);

    //email 로 User 찾기
    @Query(value = "SELECT u FROM Users u WHERE u.email =:email")
    Users findByEmail(@Param("email") String email);
}
