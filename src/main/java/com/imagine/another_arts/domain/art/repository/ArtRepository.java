package com.imagine.another_arts.domain.art.repository;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.art.repository.custom.ArtQueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtRepository extends JpaRepository<Art, Long>, ArtQueryDslRepository {
    boolean existsByName(String name);
    boolean existsByIdNotAndName(Long artId, String name);
}
