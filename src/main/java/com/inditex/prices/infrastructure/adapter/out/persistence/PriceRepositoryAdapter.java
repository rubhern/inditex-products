package com.inditex.prices.infrastructure.adapter.out.persistence;

import com.inditex.prices.domain.model.entities.Price;
import com.inditex.prices.domain.ports.out.repository.PriceQueryRepository;
import com.inditex.prices.infrastructure.adapter.out.persistence.mapper.PricePersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceQueryRepository {

    private final PriceJpaRepository jpaRepository;
    private final PricePersistenceMapper mapper;

    @Override
    public Optional<Price> findApplicablePrices(Long brandId, Long productId, LocalDateTime dateTime) {
        return jpaRepository.findApplicablePrices(brandId, productId, dateTime)
                .map(mapper::toDomain);
    }
}
