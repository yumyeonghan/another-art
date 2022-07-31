package com.imagine.another_arts.domain.art.service.dto;

import com.imagine.another_arts.domain.user.Users;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSortDto {
    private Long userId;
    private String nickname;
    private String schoolName;

    public UserSortDto(Users user){
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.schoolName = user.getSchoolName();
    }
}
