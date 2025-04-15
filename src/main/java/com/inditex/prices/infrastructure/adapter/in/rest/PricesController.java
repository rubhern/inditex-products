package com.inditex.prices.infrastructure.adapter.in.rest;

import com.inditex.prices.application.service.ApplicablePriceService;
import com.inditex.prices.infrastructure.adapter.in.rest.api.PricesApi;
import com.inditex.prices.infrastructure.adapter.in.rest.dto.PriceResponse;
import com.inditex.prices.infrastructure.adapter.in.rest.mapper.PriceRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
@RequiredArgsConstructor
public class PricesController implements PricesApi {

    private final ApplicablePriceService applicablePriceService;
    private final PriceRestMapper priceRestMapper;

    @Override
    public ResponseEntity<PriceResponse> getApplicablePrice(Long brandId, Long productId, OffsetDateTime applicationDate) {
        var price = applicablePriceService.getApplicablePrice(brandId, productId, applicationDate.toLocalDateTime());
        return ResponseEntity.ok(priceRestMapper.toResponse(price));
    }
}
