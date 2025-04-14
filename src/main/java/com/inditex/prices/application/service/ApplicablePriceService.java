package com.inditex.prices.application.service;

import com.inditex.prices.application.query.ApplicablePriceHandler;
import com.inditex.prices.application.query.ApplicablePriceQuery;
import com.inditex.prices.domain.model.entities.Price;
import com.inditex.prices.domain.ports.in.usecases.ApplicablePriceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApplicablePriceService implements ApplicablePriceUseCase {

    private final ApplicablePriceHandler handler;

    @Override
    public Optional<Price> getApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        ApplicablePriceQuery query = ApplicablePriceQuery.builder()
                .brandId(brandId)
                .productId(productId)
                .applicationDate(applicationDate)
                .build();

        return handler.handle(query);
    }
}
