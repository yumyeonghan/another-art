package com.imagine.another_arts.domain.purchase.repository;

import com.imagine.another_arts.domain.purchase.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
    Optional<PurchaseHistory> findFirstByArtId(Long artId);
}
