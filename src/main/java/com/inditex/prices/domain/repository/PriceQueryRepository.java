package com.inditex.prices.domain.repository;

import com.inditex.prices.domain.model.entities.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceQueryRepository {
    List<Price> findApplicablePrices(final Long brandId, final Long productId, final LocalDateTime dateTime);
}
