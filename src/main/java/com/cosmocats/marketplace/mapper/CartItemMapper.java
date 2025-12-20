package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.CartItem;
import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.CartItemDto;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CartItemMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "productPrice")
    @Mapping(expression = "java(domain.getSubtotal())", target = "subtotal")
    CartItemDto toDto(CartItem domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "product", ignore = true)
    CartItem toDomainWithoutIdCartAndProduct(CartItemDto dto);

    List<CartItemDto> toDtoList(List<CartItem> domains);

    default Product mapProductIdToProduct(UUID productId) {
        if (productId == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productId);
        return product;
    }
}
