package com.inditex.prices.application.query;

import com.inditex.prices.domain.model.entities.Price;
import com.inditex.prices.domain.model.enums.Currency;
import com.inditex.prices.domain.ports.out.repository.PriceQueryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicablePriceHandlerTest {

    @Mock
    private PriceQueryRepository repository;
    @InjectMocks
    private ApplicablePriceHandler handler;

    public static LocalDateTime date = LocalDateTime.of(2025, 1, 30, 0, 0);
    private static ApplicablePriceQuery query;

    @BeforeAll
    public static void setUp() {
        query = ApplicablePriceQuery.builder()
                .applicationDate(date)
                .brandId(1L)
                .productId(35455L)
                .build();
    }

    @Test
    void should_return_price_with_highest_priority() {
        // Given
        Price lowerPriority = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(1)
                .startDate(LocalDateTime.of(2025, 1, 14, 0, 0))
                .endDate(LocalDateTime.of(2025, 12, 31, 23, 59))
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency(Currency.EUR)
                .build();

        Price higherPriority = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(2)
                .startDate(LocalDateTime.of(2025, 1, 14, 15, 0))
                .endDate(LocalDateTime.of(2025, 12, 14, 18, 30))
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency(Currency.EUR)
                .build();

        List<Price> prices = Arrays.asList(lowerPriority, higherPriority);
        when(repository.findApplicablePrices(1L, 35455L, date)).thenReturn(prices);

        // When
        Optional<Price> result = handler.handle(query);

        // Then
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getPriority());
        assertEquals(new BigDecimal("25.45"), result.get().getPrice());
    }

    @Test
    void should_return_empty_if_no_applicable_price() {
        // Given
        when(repository.findApplicablePrices(1L, 35455L, date)).thenReturn(List.of());

        // When
        Optional<Price> result = handler.handle(query);

        // Then
        assertTrue(result.isEmpty());
    }
}