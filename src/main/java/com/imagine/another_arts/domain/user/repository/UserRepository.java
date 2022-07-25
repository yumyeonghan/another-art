package com.imagine.another_arts.domain.user.repository;

import com.imagine.another_arts.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

}
