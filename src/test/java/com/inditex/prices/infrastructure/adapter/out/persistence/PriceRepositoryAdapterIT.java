package com.inditex.prices.infrastructure.adapter.out.persistence;

import com.inditex.prices.domain.model.entities.Price;
import com.inditex.prices.domain.ports.out.repository.PriceQueryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PriceRepositoryAdapterIT {

    @Autowired
    private PriceQueryRepository adapter;

    @Test
    void should_return_prices_from_database() {
        // Given
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        // When
        Optional<Price> optionalPrice = adapter.findApplicablePrices(brandId, productId, applicationDate);

        // Then
        assertFalse(optionalPrice.isEmpty());
        Price price = optionalPrice.get();
        assertEquals(brandId, price.getBrandId());
        assertEquals(productId, price.getProductId());
        assertFalse(price.getStartDate().isAfter(applicationDate));
        assertFalse(price.getEndDate().isBefore(applicationDate));
        assertTrue(price.getPrice().compareTo(BigDecimal.ZERO) >= 0);
    }
}
