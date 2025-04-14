package com.inditex.prices.infrastructure.adapter.out.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
@Getter
@Setter
public class PriceEntity {
    @EmbeddedId
    private PriceId id;

    @Column(name = "price_list", nullable = false)
    private Integer priceList;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;
}
