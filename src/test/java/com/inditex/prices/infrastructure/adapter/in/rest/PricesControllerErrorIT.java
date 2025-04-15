package com.inditex.prices.infrastructure.adapter.in.rest;

import com.inditex.prices.application.service.ApplicablePriceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PricesControllerErrorIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApplicablePriceService service;

    @Test
    void should_return_500_on_unexpected_error() throws Exception {
        when(service.getApplicablePrice(any(), any(), any()))
                .thenThrow(new RuntimeException("Simulated internal error"));

        mockMvc.perform(get("/prices")
                        .param("brandId", "1")
                        .param("productId", "1")
                        .param("applicationDate", "2020-06-14T10:00:00Z"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal server error. Please contact support."));
    }
}