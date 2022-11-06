package com.imagine.another_arts.domain.user.service;

import com.imagine.another_arts.domain.login.dto.UserSessionDto;
import com.imagine.another_arts.domain.point.PointHistory;
import com.imagine.another_arts.domain.point.repository.PointHistoryRepository;
import com.imagine.another_arts.domain.user.User;
import com.imagine.another_arts.domain.user.repository.UserRepository;
import com.imagine.another_arts.domain.user.service.dto.request.UserEditRequestDto;
import com.imagine.another_arts.domain.user.service.dto.response.MyPageUserResponse;
import com.imagine.another_arts.exception.AnotherArtException;
import com.imagine.another_arts.web.SessionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.imagine.another_arts.exception.AnotherArtErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public Long saveUser(User user) {
        User saveUser = userRepository.save(user);
        pointHistoryRepository.save(PointHistory.createPointHistory(user));
        return saveUser.getId();
    }


    @Transactional
    public void editUser(Long userId, UserEditRequestDto userEditRequest) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> AnotherArtException.type(USER_NOT_FOUND));

        if (StringUtils.hasText(userEditRequest.getName())) {
            findUser.changeName(userEditRequest.getName());
        }

        if (StringUtils.hasText(userEditRequest.getNickname())) {
            checkDuplicateNicknameInModification(findUser.getId(), userEditRequest.getNickname());
            findUser.changeNickname(userEditRequest.getNickname());
        }

        if (StringUtils.hasText(userEditRequest.getSchoolName())) {
            findUser.changeSchoolName(userEditRequest.getSchoolName());
        }

        if (StringUtils.hasText(userEditRequest.getPhoneNumber())) {
            checkDuplicatePhoneNumberInModification(findUser.getId(), userEditRequest.getPhoneNumber());
            findUser.changePhoneNumber(userEditRequest.getPhoneNumber());
        }

        if (StringUtils.hasText(userEditRequest.getAddress())) {
            findUser.changeAddress(userEditRequest.getAddress());
        }
    }

    public void checkDuplicateNicknameInModification(Long userId, String changeNickname) {
        if (userRepository.existsByIdNotAndNickname(userId, changeNickname)) {
            throw AnotherArtException.type(DUPLICATE_USER_NICKNAME);
        }
    }

    public void checkDuplicatePhoneNumberInModification(Long userId, String changePhoneNumber) {
        if (userRepository.existsByIdNotAndPhoneNumber(userId, changePhoneNumber)) {
            throw AnotherArtException.type(DUPLICATE_USER_PHONENUMBER);
        }
    }

    public void hasDuplicateNickname(String nickName) {
        if (userRepository.existsByNickname(nickName)) {
            throw AnotherArtException.type(DUPLICATE_USER_NICKNAME);
        }
    }

    public void hasDuplicateLoginId(String loginId) {
        if (userRepository.existsByLoginId(loginId)) {
            throw AnotherArtException.type(DUPLICATE_USER_LOGIN_ID);
        }
    }

    public void hasDuplicatePhoneNumber(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw AnotherArtException.type(DUPLICATE_USER_PHONENUMBER);
        }
    }

    public void hasDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw AnotherArtException.type(DUPLICATE_USER_EMAIL);
        }
    }

    public MyPageUserResponse getUserInformation(HttpServletRequest request, Long userId) {
        UserSessionDto currentUserSession = getCurrentUserSession(request);
        if (!Objects.equals(currentUserSession.getId(), userId)) {
            throw AnotherArtException.type(ILLEGAL_USER_API_REQUEST);
        }

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> AnotherArtException.type(USER_NOT_FOUND));
        Long currentUserTotalPoint = pointHistoryRepository.findLatestPointByUserId(userId);

        return new MyPageUserResponse(findUser, currentUserTotalPoint);
    }

    private UserSessionDto getCurrentUserSession(HttpServletRequest request) {
        return (UserSessionDto) request.getSession(false).getAttribute(SessionFactory.ANOTHER_ART_SESSION_KEY);
    }
}