package com.inditex.prices.domain.model.entities;

import com.inditex.prices.domain.model.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Price {

    private final Long brandId;
    private final Long productId;
    private final Integer priceList;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final int priority;
    private final BigDecimal price;
    private final Currency currency;

    private Price(Builder builder) {
        this.brandId = requireNonNull(builder.brandId, "brandId is required");
        this.productId = requireNonNull(builder.productId, "productId is required");
        this.priceList = requireNonNull(builder.priceList, "priceList is required");
        this.startDate = requireNonNull(builder.startDate, "startDate is required");
        this.endDate = requireNonNull(builder.endDate, "endDate is required");
        this.priority = builder.priority;
        this.price = requireNonNull(builder.price, "price is required");
        this.currency = requireNonNull(builder.currency, "currency is required");

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must be before or equal to endDate");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price must be non-negative");
        }
    }

    private static <T> T requireNonNull(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public boolean isApplicableAt(LocalDateTime dateTime) {
        return (startDate.isEqual(dateTime) || startDate.isBefore(dateTime)) &&
                (endDate.isEqual(dateTime) || endDate.isAfter(dateTime));
    }

    // Getters
    public Long getBrandId() { return brandId; }
    public Long getProductId() { return productId; }
    public Integer getPriceList() { return priceList; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public int getPriority() { return priority; }
    public BigDecimal getPrice() { return price; }
    public Currency getCurrency() { return currency; }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long brandId;
        private Long productId;
        private Integer priceList;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private int priority;
        private BigDecimal price;
        private Currency currency;

        public Builder brandId(Long brandId) {
            this.brandId = brandId;
            return this;
        }

        public Builder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public Builder priceList(Integer priceList) {
            this.priceList = priceList;
            return this;
        }

        public Builder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Price build() {
            return new Price(this);
        }
    }

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;
        Price price1 = (Price) o;
        return priority == price1.priority &&
                Objects.equals(brandId, price1.brandId) &&
                Objects.equals(productId, price1.productId) &&
                Objects.equals(priceList, price1.priceList) &&
                Objects.equals(startDate, price1.startDate) &&
                Objects.equals(endDate, price1.endDate) &&
                Objects.equals(price, price1.price) &&
                currency == price1.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandId, productId, priceList, startDate, endDate, priority, price, currency);
    }

    @Override
    public String toString() {
        return "Price{" +
                "brandId=" + brandId +
                ", productId=" + productId +
                ", priceList=" + priceList +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", priority=" + priority +
                ", price=" + price +
                ", currency=" + currency +
                '}';
    }
}
