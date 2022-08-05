package com.imagine.another_arts;

import com.imagine.another_arts.domain.user.Users;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TestInit {

    private final UserRepository userRepository;

    @PostConstruct
    public void init(){

        Users user = Users.createUser("1","1","1","1",
                "1","1","1","1", LocalDate.parse("1111-11-11"));

        userRepository.save(user);
    }
}
