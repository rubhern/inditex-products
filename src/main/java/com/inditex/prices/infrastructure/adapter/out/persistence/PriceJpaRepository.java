package com.inditex.prices.infrastructure.adapter.out.persistence;

import com.inditex.prices.infrastructure.adapter.out.persistence.model.PriceEntity;
import com.inditex.prices.infrastructure.adapter.out.persistence.model.PriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, PriceId> {
    @Query("""
        SELECT p FROM PriceEntity p
        WHERE p.id.brandId = :brandId
          AND p.id.productId = :productId
          AND :applicationDate BETWEEN p.id.startDate AND p.id.endDate
    """)
    List<PriceEntity> findApplicablePrices(final Long brandId, final Long productId, final LocalDateTime applicationDate);
}
