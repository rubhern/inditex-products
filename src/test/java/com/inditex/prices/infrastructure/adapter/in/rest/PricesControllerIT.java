package com.inditex.prices.infrastructure.adapter.in.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class PricesControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private static final String URL = "/prices";

    @Test
    void should_return_200_when_valid_request() throws Exception {
        mockMvc.perform(get(URL)
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("applicationDate", "2020-06-14T10:00:00Z"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455));
    }

    @Test
    void should_return_404_when_price_not_found() throws Exception {
        mockMvc.perform(get(URL)
                        .param("brandId", "999")
                        .param("productId", "999")
                        .param("applicationDate", "2020-06-14T10:00:00Z"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void should_return_400_for_missing_param() throws Exception {
        mockMvc.perform(get(URL)
                        .param("brandId", "1")
                        .param("applicationDate", "2020-06-14T10:00:00Z"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("Missing required parameter")));
    }

    @Test
    void should_return_400_for_invalid_param_type() throws Exception {
        mockMvc.perform(get(URL)
                        .param("brandId", "abc")
                        .param("productId", "35455")
                        .param("applicationDate", "2020-06-14T10:00:00Z"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("Invalid value")));
    }

    @Test
    void should_return_400_for_invalid_date_format() throws Exception {
        mockMvc.perform(get(URL)
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("applicationDate", "invalid-date"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("Expected type: OffsetDateTime")));
    }
}