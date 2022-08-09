package com.imagine.another_arts.domain.art.service.dto;

import com.imagine.another_arts.domain.user.Users;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SortedArtUserDto {
    private Long userId; // 유저 ID(PK)
    private String nickname; // 유저 닉네임
    private String schoolName; // 유저 재학중인 학교명

    public SortedArtUserDto(Users user){
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.schoolName = user.getSchoolName();
    }
}
