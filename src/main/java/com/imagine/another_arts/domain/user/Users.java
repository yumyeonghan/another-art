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
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 30) // 한글[10자]
    private String name;

    @Column(name = "nickname", nullable = false, length = 30) // 한글[10자], 영어[30자]
    private String nickname;

    @Column(name = "login_id", nullable = false, unique = true, length = 30)
    private String loginId;

    @Column(name = "login_password", nullable = false, length = 100) // 길이 100은 나중에 암호화 사용할 수 있으므로
    private String loginPassword;

    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;

    @Column(name = "school_name", nullable = false, length = 35)
    private String schoolName;

    @Column(name = "phone_number", nullable = false, unique = true, length = 11)
    private String phoneNumber;

    @Column(name = "address", nullable = false, length = 200) // 추후에 임베디드 타입으로 나누기 or 그냥 전체를 하나 컬럼에 insert
    private String address;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    //==생성 메소드==//
    public static Users createUser(String name, String nickname, String loginId, String loginPassword,
                 String email, String schoolName, String phoneNumber, String address, LocalDate birth) {
        Users user = new Users();
        user.name = name;
        user.nickname = nickname;
        user.loginId = loginId;
        user.loginPassword = loginPassword;
        user.email=email;
        user.schoolName = schoolName;
        user.phoneNumber = phoneNumber;
        user.address = address;
        user.birth = birth;
        return user;
    }

    //==관련 비즈니스 로직 작성 공간==//
    
    // 닉네임 변경
    public void changeNickname(String nickname){
        this.nickname = nickname;
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

    //==테스트를 위한 toString()==//

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", loginId='" + loginId + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                ", email='" + email + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", birth=" + birth +
                '}';
    }
}