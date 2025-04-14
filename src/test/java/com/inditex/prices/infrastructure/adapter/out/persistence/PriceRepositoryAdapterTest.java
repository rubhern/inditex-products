package com.inditex.prices.infrastructure.adapter.out.persistence;

import com.inditex.prices.domain.model.entities.Price;
import com.inditex.prices.infrastructure.adapter.out.persistence.mapper.PricePersistenceMapper;
import com.inditex.prices.infrastructure.adapter.out.persistence.model.PriceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceRepositoryAdapterTest {

    @Mock
    private PriceJpaRepository jpaRepository;
    @Mock
    private PricePersistenceMapper mapper;
    @InjectMocks
    private PriceRepositoryAdapter adapter;

    @Test
    void should_return_mapped_prices() {
        // Given
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);

        PriceEntity entity = mock(PriceEntity.class);
        Price price = mock(Price.class);

        when(jpaRepository.findApplicablePrices(brandId, productId, date)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(price);

        // When
        List<Price> result = adapter.findApplicablePrices(1L, 35455L, date);

        // Then
        assertEquals(1, result.size());
        assertEquals(price, result.getFirst());

        verify(jpaRepository).findApplicablePrices(brandId, productId, date);
        verify(mapper).toDomain(entity);
    }
}