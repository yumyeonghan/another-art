package com.imagine.another_arts.domain.user.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
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
}