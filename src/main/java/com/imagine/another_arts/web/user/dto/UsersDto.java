package com.imagine.another_arts.web.user.dto;

import com.imagine.another_arts.domain.user.Users;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UsersDto {

    private String name;

    private String nickname;

    private String loginId;

    private String loginPassword;

    private String schoolName;

    private String phoneNumber;

    private String address;

    private LocalDate birth;

    public UsersDto(Users users) {
        this.name = users.getName();
        this.nickname = users.getName();
        this.loginId = users.getLoginId();
        this.loginPassword = users.getLoginPassword();
        this.schoolName = users.getSchoolName();
        this.phoneNumber = users.getPhoneNumber();
        this.address = users.getAddress();
        this.birth = users.getBirth();
    }
}
