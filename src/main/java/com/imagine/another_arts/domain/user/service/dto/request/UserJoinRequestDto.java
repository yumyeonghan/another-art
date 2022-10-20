package com.imagine.another_arts.domain.user.service.dto.request;

import com.imagine.another_arts.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UserJoinRequestDto {
    private String name;
    private String nickname;
    private String loginId;
    private String loginPassword;
    private String email;
    private String schoolName;
    private String phoneNumber;
    private String address;
    private LocalDate birth;

    public User toEntity() {
        return User.createUser(
                this.getName(),
                this.getNickname(),
                this.getLoginId(),
                this.getLoginPassword(),
                this.getEmail(),
                this.getSchoolName(),
                this.getPhoneNumber(),
                this.getAddress(),
                this.getBirth()
        );
    }
}
