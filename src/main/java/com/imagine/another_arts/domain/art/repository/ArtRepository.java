package com.imagine.another_arts.domain.art.repository;

import com.imagine.another_arts.domain.art.Art;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtRepository extends JpaRepository<Art, Long> {

}
