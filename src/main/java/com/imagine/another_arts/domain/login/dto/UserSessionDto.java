package com.imagine.another_arts.domain.login.dto;

import com.imagine.another_arts.domain.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserSessionDto {
    private Long id;
    private String name;
    private String nickname;
    private String loginId;
    private String email;
    private String schoolName;
    private String phoneNumber;
    private String address;
    private LocalDate birth;

    public UserSessionDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.loginId = user.getLoginId();
        this.email = user.getEmail();
        this.schoolName = user.getSchoolName();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress();
        this.birth = user.getBirth();
    }
}
