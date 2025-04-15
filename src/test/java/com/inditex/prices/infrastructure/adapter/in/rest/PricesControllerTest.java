package com.inditex.prices.infrastructure.adapter.in.rest;

import com.inditex.prices.application.service.ApplicablePriceService;
import com.inditex.prices.domain.model.entities.Price;
import com.inditex.prices.infrastructure.adapter.in.rest.dto.PriceResponse;
import com.inditex.prices.infrastructure.adapter.in.rest.mapper.PriceRestMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PricesControllerTest {

    @Mock
    private ApplicablePriceService service;

    @Mock
    private PriceRestMapper mapper;

    @InjectMocks
    private PricesController controller;

    @Test
    void should_return_response_when_price_found() {
        // Given
        Long brandId = 1L;
        Long productId = 35455L;
        OffsetDateTime date = OffsetDateTime.parse("2020-06-14T10:00:00Z");

        Price price = mock(Price.class);
        PriceResponse response = mock(PriceResponse.class);

        when(service.getApplicablePrice(brandId, productId, date.toLocalDateTime())).thenReturn(price);
        when(mapper.toResponse(price)).thenReturn(response);

        // When
        ResponseEntity<PriceResponse> result = controller.getApplicablePrice(brandId, productId, date);

        // Then
        assertEquals(200, result.getStatusCode().value());
        assertEquals(response, result.getBody());
    }
}