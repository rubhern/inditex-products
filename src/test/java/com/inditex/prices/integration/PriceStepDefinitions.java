package com.inditex.prices.integration;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceStepDefinitions {

    @LocalServerPort
    private int port;

    private ResponseEntity<String> response;
    private final RestTemplate restTemplate = new RestTemplate();

    @Given("the application is running")
    public void the_application_is_running() {
        assertTrue(port > 0);
    }

    @When("I request the price for product {long} from brand {long} at {string}")
    public void i_request_the_price(Long productId, Long brandId, String date) {

        String url = String.format("http://localhost:%d/prices?productId=%d&brandId=%d&applicationDate=%s",
                port, productId, brandId, date);
        response = restTemplate.getForEntity(url, String.class);
    }

    @Then("the response should contain product {long}, brand {long}, price {string}, startDate {string} and endDate {string}")
    public void the_response_should_contain(Long expectedProductId, Long expectedBrandId, String expectedPrice, String expectedStartDate, String expectedEndDate) {
        assertEquals(200, response.getStatusCode().value());
        String body = response.getBody();
        assertNotNull(body);
        assertTrue(body.contains("\"productId\":" + expectedProductId));
        assertTrue(body.contains("\"brandId\":" + expectedBrandId));
        assertTrue(body.contains("\"startDate\":" + "\"" + expectedStartDate + "\""));
        assertTrue(body.contains("\"endDate\":" + "\"" + expectedEndDate + "\""));
        assertTrue(body.contains("\"price\":" + "\"" + expectedPrice + "\""));
    }
}
