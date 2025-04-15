Feature: Price consultation

  Scenario Outline: Query product price at a specific date and time
    Given the application is running
    When I request the price for product <productId> from brand <brandId> at "<applicationDate>"
    Then the response should contain product <productId>, brand <brandId>, price "<expectedPrice>", startDate "<expectedStartDate>" and endDate "<expectedEndDate>"

    Examples:
      | productId | brandId | applicationDate          | expectedPrice   | expectedStartDate    | expectedEndDate      |
      | 35455     | 1       | 2020-06-14T10:00:00Z     | 35.50 EUR       | 2020-06-14T00:00:00Z | 2020-12-31T23:59:59Z |
      | 35455     | 1       | 2020-06-14T16:00:00Z     | 25.45 EUR       | 2020-06-14T15:00:00Z | 2020-06-14T18:30:00Z |
      | 35455     | 1       | 2020-06-14T21:00:00Z     | 35.50 EUR       | 2020-06-14T00:00:00Z | 2020-12-31T23:59:59Z |
      | 35455     | 1       | 2020-06-15T10:00:00Z     | 30.50 EUR       | 2020-06-15T00:00:00Z | 2020-06-15T11:00:00Z |
      | 35455     | 1       | 2020-06-16T21:00:00Z     | 38.95 EUR       | 2020-06-15T16:00:00Z | 2020-12-31T23:59:59Z |