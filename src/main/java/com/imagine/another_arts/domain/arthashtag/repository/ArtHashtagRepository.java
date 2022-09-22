package com.imagine.another_arts.domain.arthashtag.repository;

import com.imagine.another_arts.domain.arthashtag.ArtHashtag;
import com.imagine.another_arts.domain.arthashtag.repository.custom.ArtHashtagQueryDSLRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtHashtagRepository extends JpaRepository<ArtHashtag, Long>, ArtHashtagQueryDSLRepository {

}