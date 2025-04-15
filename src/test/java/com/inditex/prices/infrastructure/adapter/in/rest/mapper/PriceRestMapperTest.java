package com.inditex.prices.infrastructure.adapter.in.rest.mapper;

import com.inditex.prices.domain.model.entities.Price;
import com.inditex.prices.domain.model.enums.Currency;
import com.inditex.prices.infrastructure.adapter.in.rest.dto.PriceResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceRestMapperTest {
    private final PriceRestMapper mapper = new PriceRestMapperImpl();

    @Test
    void should_map_price_entity_to_response_dto_correctly() {
        // Given
        Price price = Price.builder()
                .productId(35455L)
                .brandId(1L)
                .priceList(2)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .price(new BigDecimal("25.45"))
                .currency(Currency.EUR)
                .build();

        // When
        PriceResponse response = mapper.toResponse(price);

        // Then
        assertEquals(35455L, response.getProductId());
        assertEquals(1L, response.getBrandId());
        assertEquals(2, response.getPriceList());
        assertEquals(OffsetDateTime.of(2020, 6, 14, 15, 0, 0, 0, ZoneOffset.UTC), response.getStartDate());
        assertEquals(OffsetDateTime.of(2020, 6, 14, 18, 30, 0, 0, ZoneOffset.UTC), response.getEndDate());
        assertEquals("25.45 EUR", response.getPrice());
    }
}