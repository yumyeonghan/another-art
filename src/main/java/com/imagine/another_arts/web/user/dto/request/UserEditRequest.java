package com.imagine.another_arts.web.user.dto.request;

import com.imagine.another_arts.domain.user.service.dto.request.UserEditRequestDto;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserEditRequest {
    @ApiParam(value = "사용자 이름")
    private String name;

    @ApiParam(value = "사용자 닉네임")
    private String nickname;

    @ApiParam(value = "사용자 재학중인 학교명")
    private String schoolName;

    @ApiParam(value = "사용자 전화번호")
    private String phoneNumber;

    @ApiParam(value = "사용자 주소")
    private String address;

    public UserEditRequestDto toServiceDto() {
        return new UserEditRequestDto(
                this.name,
                this.nickname,
                this.schoolName,
                this.phoneNumber,
                this.address
        );
    }
}