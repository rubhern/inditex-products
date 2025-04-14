package com.inditex.prices.application.query;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ApplicablePriceQuery {
    private final Long brandId;
    private final Long productId;
    private final LocalDateTime applicationDate;
}
