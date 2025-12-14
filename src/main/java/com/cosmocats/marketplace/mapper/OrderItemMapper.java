package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.OrderItem;
import com.cosmocats.marketplace.dto.OrderItemDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderItemMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(expression = "java(entity.getSubtotal())", target = "subtotal")
    OrderItemDto toDto(OrderItem entity);

    List<OrderItemDto> toDtoList(List<OrderItem> entities);
}
