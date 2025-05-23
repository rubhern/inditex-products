package com.inditex.prices.application.service;

import com.inditex.prices.application.query.ApplicablePriceHandler;
import com.inditex.prices.application.query.ApplicablePriceQuery;
import com.inditex.prices.domain.exception.PriceNotFoundException;
import com.inditex.prices.domain.model.entities.Price;
import com.inditex.prices.domain.ports.in.usecases.ApplicablePriceUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicablePriceService implements ApplicablePriceUseCase {

    private final ApplicablePriceHandler handler;

    @Override
    public Price getApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        ApplicablePriceQuery query = ApplicablePriceQuery.builder()
                .brandId(brandId)
                .productId(productId)
                .applicationDate(applicationDate)
                .build();

        return handler.handle(query).orElseThrow(() -> {
            log.warn("No price found for brandId={}, productId={}, date={}", brandId, productId, applicationDate);
            return new PriceNotFoundException(
                    "No price found for brandId=" + brandId +
                            ", productId=" + productId +
                            ", applicationDate=" + applicationDate);
        });
    }
}
