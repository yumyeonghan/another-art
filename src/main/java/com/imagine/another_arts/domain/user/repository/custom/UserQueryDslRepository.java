package com.imagine.another_arts.domain.user.repository.custom;

import com.imagine.another_arts.domain.user.User;

public interface UserQueryDslRepository {
    // [로그인 아이디]로 User 찾기 (사전에 세션 존재유무 확인을 통해서 해당 유저는 반드시 존재함을 가정)
    User findUserWithSessionByLoginId(String loginId);
}
