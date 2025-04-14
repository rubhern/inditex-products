package com.inditex.prices.application.query;

import com.inditex.prices.domain.model.entities.Price;
import com.inditex.prices.domain.ports.out.repository.PriceQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
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
