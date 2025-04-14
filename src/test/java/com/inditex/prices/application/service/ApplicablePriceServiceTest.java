package com.inditex.prices.application.service;

import com.inditex.prices.application.query.ApplicablePriceHandler;
import com.inditex.prices.application.query.ApplicablePriceQuery;
import com.inditex.prices.domain.model.entities.Price;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicablePriceServiceTest {

    @Mock
    private ApplicablePriceHandler handler;
    @InjectMocks
    private ApplicablePriceService service;

    @Test
    void should_delegate_to_handler_and_return_result() {
        // Given
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);

        Price expectedPrice = Price.builder()
                .brandId(brandId)
                .productId(productId)
                .priceList(2)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 0))
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency(com.inditex.prices.domain.model.enums.Currency.EUR)
                .build();

        when(handler.handle(any(ApplicablePriceQuery.class))).thenReturn(Optional.of(expectedPrice));

        // When
        Optional<Price> result = service.getApplicablePrice(brandId, productId, date);

        // Then
        assertTrue(result.isPresent());
        assertEquals(expectedPrice, result.get());

        verify(handler, times(1)).handle(argThat(query ->
                query.getBrandId().equals(brandId) &&
                        query.getProductId().equals(productId) &&
                        query.getApplicationDate().equals(date)
        ));
    }
}