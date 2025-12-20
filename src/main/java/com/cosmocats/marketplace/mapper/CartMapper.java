package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Cart;
import com.cosmocats.marketplace.dto.CartDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {CartItemMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CartMapper {

    @Mapping(expression = "java(domain.getTotalAmount())", target = "totalAmount")
    @Mapping(expression = "java(domain.getTotalItems())", target = "totalItems")
    CartDto toDto(Cart domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    Cart toDomainWithoutIdTimestampsAndItems(CartDto dto);

    List<CartDto> toDtoList(List<Cart> domains);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    void updateDomainFromDtoWithoutIdTimestampsAndItems(CartDto dto, @MappingTarget Cart domain);
}
