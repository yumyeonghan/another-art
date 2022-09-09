package com.imagine.another_arts.web.user.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserJoinRequest {
    @NotBlank(message = "이름은 필수입니다")
    @ApiParam(value = "사용자 이름", required = true)
    private String name;

    @NotBlank(message = "닉네임은 필수입니다")
    @ApiParam(value = "사용자 닉네임", required = true)
    private String nickname;

    @NotBlank(message = "아이디는 필수입니다")
    @ApiParam(value = "로그인 아이디", required = true)
    private String loginId;

    @NotBlank(message = "비밀번호는 필수입니다")
    @ApiParam(value = "로그인 비밀번호", required = true)
    private String loginPassword;

    @NotBlank(message = "이메일은 필수입니다")
    @ApiParam(value = "사용자 이메일", required = true)
    private String email;

    @NotBlank(message = "학교는 필수입니다")
    @ApiParam(value = "사용자 재학중인 학교명", required = true)
    private String schoolName;

    @NotBlank(message = "전화번호는 필수입니다")
    @ApiParam(value = "사용자 전화번호", required = true)
    private String phoneNumber;

    @NotBlank(message = "주소는 필수입니다")
    @ApiParam(value = "사용자 주소", required = true)
    private String address;

    @NotNull(message = "생년월일은 필수입니다")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiParam(value = "사용자 생년월일", required = true, example = "2022-08-22")
    private LocalDate birth;
}
