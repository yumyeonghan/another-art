package com.imagine.another_arts.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "nickname", nullable = false, unique = true, length = 30)
    private String nickname;

    @Column(name = "login_id", nullable = false, unique = true, updatable = false, length = 30)
    private String loginId;

    @Column(name = "login_password", nullable = false, length = 100)
    private String loginPassword;

    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;

    @Column(name = "school_name", nullable = false, length = 35)
    private String schoolName;

    @Column(name = "phone_number", nullable = false, unique = true, length = 11)
    private String phoneNumber;

    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "available_point", nullable = false)
    private Long availablePoint;

    //==생성 메소드==//
    public static User createUser(String name, String nickname, String loginId, String loginPassword,
                                  String email, String schoolName, String phoneNumber, String address, LocalDate birth) {
        User user = new User();
        user.name = name;
        user.nickname = nickname;
        user.loginId = loginId;
        user.loginPassword = loginPassword;
        user.email = email;
        user.schoolName = schoolName;
        user.phoneNumber = phoneNumber;
        user.address = address;
        user.birth = birth;
        user.availablePoint = 0L;
        return user;
    }

    //==관련 비즈니스 로직 작성 공간==//
    // 이름 변경
    public void changeName(String name){
        this.name = name;
    }

    // 닉네임 변경
    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    // 학교 이름 변경
    public void changeSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    // 전화번호 변경
    public void changePhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    // 주소 변경
    public void changeAddress(String address){
        this.address = address;
    }

    // 비밀번호 변경
    public void changePassword(String loginPassword){
        this.loginPassword = loginPassword;
    }

    // 사용가능 포인트 변경
    public void changeAvailablePoint(Long availablePoint) { this.availablePoint = availablePoint; }
}