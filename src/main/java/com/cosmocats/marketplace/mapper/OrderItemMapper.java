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
    @Mapping(expression = "java(domain.getSubtotal())", target = "subtotal")
    OrderItemDto toDto(OrderItem domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderItem toDomainWithoutIdOrderAndProduct(OrderItemDto dto);

    List<OrderItemDto> toDtoList(List<OrderItem> domains);

    default Product mapProductIdToProduct(UUID productId) {
        if (productId == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productId);
        return product;
    }
}
