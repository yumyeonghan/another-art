package com.imagine.another_arts.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserEditForm {

    private Long id;

    private String name;

    private String nickname;

    private String schoolName;

    private String phoneNumber;

    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
}
