package com.inditex.prices.infrastructure.adapter.out.persistence;

import com.inditex.prices.infrastructure.adapter.out.persistence.model.PriceEntity;
import com.inditex.prices.infrastructure.adapter.out.persistence.model.PriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, PriceId> {
    @Query(value = """
        SELECT * FROM prices p
    WHERE p.product_id = :productId
      AND p.brand_id = :brandId
      AND :applicationDate BETWEEN p.start_date AND p.end_date
    ORDER BY p.priority DESC
    LIMIT 1
    """, nativeQuery = true)
    Optional<PriceEntity> findApplicablePrices(final Long brandId, final Long productId, final LocalDateTime applicationDate);
}
