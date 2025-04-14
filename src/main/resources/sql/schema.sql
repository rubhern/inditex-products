DROP TABLE IF EXISTS prices;

CREATE TABLE prices
(
    brand_id   BIGINT         NOT NULL,
    product_id BIGINT         NOT NULL,
    price_list INTEGER        NOT NULL,
    start_date TIMESTAMP      NOT NULL,
    end_date   TIMESTAMP      NOT NULL,
    priority   INTEGER        NOT NULL,
    price      DECIMAL(10, 2) NOT NULL,
    currency   VARCHAR(3)     NOT NULL,
    PRIMARY KEY (brand_id, product_id, start_date, end_date, priority)
);
