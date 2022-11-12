package com.imagine.another_arts.domain.purchase.repository;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.purchase.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
    boolean existsByArt(Art art);
}
