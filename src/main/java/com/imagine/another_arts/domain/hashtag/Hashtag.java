package com.imagine.another_arts.domain.hashtag;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "hashtag")
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    //==생성 메소드==//
    public static Hashtag createHashtag(String name) {
        Hashtag hashtag = new Hashtag();
        hashtag.name = name;
        return hashtag;
    }

    //==관련 비즈니스 로직 작성 공간==//
    public void changeHashtagName(String name){
        this.name = name;
    }

    //==테스트를 위한 toString()==//
    @Override
    public String toString() {
        return "\nHashtag{" +
                "\n\tid=" + id +
                ", \n\tname='" + name + '\'' +
                "\n}";
    }
}
