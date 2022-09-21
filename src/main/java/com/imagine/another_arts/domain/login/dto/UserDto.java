package com.imagine.another_arts.domain.login.dto;

import com.imagine.another_arts.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String nickname;
    private String loginId;
    private String loginPassword;
    private String email;
    private String schoolName;
    private String phoneNumber;
    private String address;
    private LocalDate birth;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.loginId = user.getLoginId();
        this.loginPassword = user.getLoginPassword();
        this.email = user.getEmail();
        this.schoolName = user.getSchoolName();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress();
        this.birth = user.getBirth();
    }
}
