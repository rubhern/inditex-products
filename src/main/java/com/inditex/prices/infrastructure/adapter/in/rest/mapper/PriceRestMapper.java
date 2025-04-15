package com.inditex.prices.infrastructure.adapter.in.rest.mapper;

import com.inditex.prices.domain.model.entities.Price;
import com.inditex.prices.infrastructure.adapter.in.rest.dto.PriceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface PriceRestMapper {

    @Mapping(source = "startDate", target = "startDate", qualifiedByName = "toOffsetDateTime")
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = "toOffsetDateTime")
    @Mapping(target = "price", source = ".", qualifiedByName = "formatPrice")
    PriceResponse toResponse(Price price);

    @Named("toOffsetDateTime")
    static OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime.atOffset(ZoneOffset.UTC);
    }

    @Named("formatPrice")
    static String formatPrice(Price price) {
        return price.getPrice() + " " + price.getCurrency().name();
    }
}
