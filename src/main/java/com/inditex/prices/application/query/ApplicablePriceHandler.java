package com.inditex.prices.application.query;

import com.inditex.prices.domain.model.entities.Price;
import com.inditex.prices.domain.ports.out.repository.PriceQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ApplicablePriceHandler {

    private final PriceQueryRepository priceQueryRepository;

    public Optional<Price> handle(ApplicablePriceQuery query) {
        return priceQueryRepository.findApplicablePrices(
                query.getBrandId(), query.getProductId(), query.getApplicationDate()
        );
    }
}
