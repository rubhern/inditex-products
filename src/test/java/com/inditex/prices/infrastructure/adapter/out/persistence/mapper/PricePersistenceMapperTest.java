package com.inditex.prices.infrastructure.adapter.out.persistence.mapper;

import com.inditex.prices.domain.model.entities.Price;
import com.inditex.prices.domain.model.enums.Currency;
import com.inditex.prices.infrastructure.adapter.out.persistence.model.PriceEntity;
import com.inditex.prices.infrastructure.adapter.out.persistence.model.PriceId;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PricePersistenceMapperTest {

    private final PricePersistenceMapper mapper = Mappers.getMapper(PricePersistenceMapper.class);

    @Test
    void should_map_entity_to_domain() {
        // Given
        PriceId id = new PriceId(1L, 35455L,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                1);

        PriceEntity entity = new PriceEntity();
        entity.setId(id);
        entity.setPriceList(2);
        entity.setPrice(new BigDecimal("25.45"));
        entity.setCurrency("EUR");

        // When
        Price price = mapper.toDomain(entity);

        // Then
        assertEquals(1L, price.getBrandId());
        assertEquals(35455L, price.getProductId());
        assertEquals(2, price.getPriceList());
        assertEquals(LocalDateTime.of(2020, 6, 14, 15, 0), price.getStartDate());
        assertEquals(LocalDateTime.of(2020, 6, 14, 18, 30), price.getEndDate());
        assertEquals(1, price.getPriority());
        assertEquals(new BigDecimal("25.45"), price.getPrice());
        assertEquals(Currency.EUR, price.getCurrency());
    }

    @Test
    void should_map_domain_to_entity() {
        // Given
        Price price = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(2)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency(Currency.EUR)
                .build();

        // When
        PriceEntity entity = mapper.toEntity(price);

        // Then
        PriceId id = entity.getId();
        assertNotNull(id);
        assertEquals(1L, id.getBrandId());
        assertEquals(35455L, id.getProductId());
        assertEquals(LocalDateTime.of(2020, 6, 14, 15, 0), id.getStartDate());
        assertEquals(LocalDateTime.of(2020, 6, 14, 18, 30), id.getEndDate());
        assertEquals(1, id.getPriority());

        assertEquals(2, entity.getPriceList());
        assertEquals(new BigDecimal("25.45"), entity.getPrice());
        assertEquals("EUR", entity.getCurrency());
    }
}