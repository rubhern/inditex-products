package com.inditex.prices.application.query;

import com.inditex.prices.domain.model.entities.Price;
import com.inditex.prices.domain.repository.PriceQueryRepository;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ApplicablePriceHandler {

    private final PriceQueryRepository priceQueryRepository;

    public Optional<Price> handle(ApplicablePriceQuery query) {
        List<Price> prices = priceQueryRepository.findApplicablePrices(
                query.getBrandId(), query.getProductId(), query.getApplicationDate()
        );

        return filterHighPriority(query, prices); // High priority first
    }

    private static Optional<Price> filterHighPriority(final ApplicablePriceQuery query, final List<Price> prices) {
        return prices.stream()
                .filter(price -> price.isApplicableAt(query.getApplicationDate()))
                .max(Comparator.comparingInt(Price::getPriority));
    }
}
