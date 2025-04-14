package com.inditex.prices.infrastructure.adapter.out.persistence.mapper;


import com.inditex.prices.domain.model.entities.Price;
import com.inditex.prices.domain.model.enums.Currency;
import com.inditex.prices.infrastructure.adapter.out.persistence.model.PriceEntity;
import com.inditex.prices.infrastructure.adapter.out.persistence.model.PriceId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PricePersistenceMapper {

    @Mapping(source = "id.brandId", target = "brandId")
    @Mapping(source = "id.productId", target = "productId")
    @Mapping(source = "id.startDate", target = "startDate")
    @Mapping(source = "id.endDate", target = "endDate")
    @Mapping(source = "id.priority", target = "priority")
    @Mapping(source = "currency", target = "currency", qualifiedByName = "toCurrencyEnum")
    Price toDomain(PriceEntity entity);

    @Mapping(target = "id", source = ".", qualifiedByName = "toPriceId")
    @Mapping(target = "currency", source = "currency", qualifiedByName = "toCurrencyString")
    PriceEntity toEntity(Price price);

    // Custom mappers
    @Named("toPriceId")
    default PriceId toPriceId(Price price) {
        return new PriceId(
                price.getBrandId(),
                price.getProductId(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPriority()
        );
    }

    @Named("toCurrencyEnum")
    default Currency toCurrencyEnum(String currency) {
        return Currency.valueOf(currency);
    }

    @Named("toCurrencyString")
    default String toCurrencyString(Currency currency) {
        return currency.name();
    }
}
