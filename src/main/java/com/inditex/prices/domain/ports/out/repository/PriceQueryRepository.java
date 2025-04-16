package com.inditex.prices.domain.ports.out.repository;

import com.inditex.prices.domain.model.entities.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceQueryRepository {
    Optional<Price> findApplicablePrices(final Long brandId, final Long productId, final LocalDateTime dateTime);
}
