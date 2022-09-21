package com.imagine.another_arts.domain.likeart.repository;

import com.imagine.another_arts.domain.likeart.LikeArt;
import com.imagine.another_arts.domain.likeart.repository.custom.LikeArtQueryDSLRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeArtRepository extends JpaRepository<LikeArt, Long>, LikeArtQueryDSLRepository {

}