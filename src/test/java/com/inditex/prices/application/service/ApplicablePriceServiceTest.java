package com.inditex.prices.application.service;

import com.inditex.prices.application.query.ApplicablePriceHandler;
import com.inditex.prices.application.query.ApplicablePriceQuery;
import com.inditex.prices.domain.exception.PriceNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        Price result = service.getApplicablePrice(brandId, productId, date);

        // Then
        assertEquals(expectedPrice, result);

        verify(handler, times(1)).handle(argThat(query ->
                query.getBrandId().equals(brandId) &&
                        query.getProductId().equals(productId) &&
                        query.getApplicationDate().equals(date)
        ));
    }

    @Test
    void should_throw_exception_when_no_price_found() {
        // Given
        Long brandId = 1L;
        Long productId = 99999L;
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);

        when(handler.handle(any(ApplicablePriceQuery.class))).thenReturn(Optional.empty());

        // When + Then
        assertThrows(PriceNotFoundException.class, () ->
                service.getApplicablePrice(brandId, productId, date)
        );

        verify(handler, times(1)).handle(any(ApplicablePriceQuery.class));
    }
}