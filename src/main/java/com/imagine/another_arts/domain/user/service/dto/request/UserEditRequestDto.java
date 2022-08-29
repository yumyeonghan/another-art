package com.imagine.another_arts.domain.user.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserEditRequestDto {
    private String name;
    private String nickname;
    private String schoolName;
    private String phoneNumber;
    private String address;
}
