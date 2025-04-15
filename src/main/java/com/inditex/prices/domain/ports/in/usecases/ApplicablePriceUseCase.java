package com.inditex.prices.domain.ports.in.usecases;

import com.inditex.prices.domain.model.entities.Price;

import java.time.LocalDateTime;

public interface ApplicablePriceUseCase {

    Price getApplicablePrice(final Long brandId, final Long productId, final LocalDateTime applicationDate);
}
