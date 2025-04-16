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
    void should_return_price() {
        // Given
        Price price = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(2)
                .startDate(LocalDateTime.of(2025, 1, 14, 15, 0))
                .endDate(LocalDateTime.of(2025, 12, 14, 18, 30))
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency(Currency.EUR)
                .build();

        when(repository.findApplicablePrices(1L, 35455L, date)).thenReturn(Optional.of(price));

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
        when(repository.findApplicablePrices(1L, 35455L, date)).thenReturn(Optional.empty());

        // When
        Optional<Price> result = handler.handle(query);

        // Then
        assertTrue(result.isEmpty());
    }
}