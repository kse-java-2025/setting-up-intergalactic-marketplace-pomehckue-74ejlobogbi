package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.OrderItem;
import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.OrderItemDto;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderItem toEntity(OrderItemDto dto);

    List<OrderItemDto> toDtoList(List<OrderItem> entities);

    default Product mapProductId(UUID productId) {
        if (productId == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productId);
        return product;
    }
}
