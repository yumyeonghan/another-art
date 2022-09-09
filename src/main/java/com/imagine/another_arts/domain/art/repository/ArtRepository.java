package com.imagine.another_arts.domain.art.repository;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.art.repository.custom.ArtCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArtRepository extends JpaRepository<Art, Long>, ArtCustomRepository {
    boolean existsByName(String name);

    @Query("SELECT a" +
            " FROM Art a" +
            " JOIN FETCH a.user" +
            " WHERE a.id = :artId")
    Optional<Art> findFirstArtBy(@Param("artId") Long artId);

    boolean existsByIdNotAndName(Long artId, String name);
}

