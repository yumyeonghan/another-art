package com.imagine.another_arts.domain.likeart.repository;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.likeart.LikeArt;
import com.imagine.another_arts.domain.likeart.repository.custom.LikeArtQueryDslRepository;
import com.imagine.another_arts.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeArtRepository extends JpaRepository<LikeArt, Long>, LikeArtQueryDslRepository {
    boolean existsByArtAndUser(Art art, User user);
}