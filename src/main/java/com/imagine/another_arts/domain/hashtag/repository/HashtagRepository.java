package com.imagine.another_arts.domain.hashtag.repository;

import com.imagine.another_arts.domain.hashtag.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
