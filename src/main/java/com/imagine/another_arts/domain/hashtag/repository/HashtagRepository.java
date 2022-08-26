package com.imagine.another_arts.domain.hashtag.repository;

import com.imagine.another_arts.domain.hashtag.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findFirstByName(String name);
}
