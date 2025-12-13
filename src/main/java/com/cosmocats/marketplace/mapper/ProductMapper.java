package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.ProductDto;
import com.cosmocats.marketplace.repository.entity.ProductEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product domain);

    Product toDomain(ProductEntity entity);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productReference", ignore = true)
    ProductEntity toEntity(ProductDto dto);

    List<ProductDto> toDtoList(List<Product> dtos);

    List<Product> toDomainList(List<ProductEntity> entities);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productReference", ignore = true)
    void updateEntityFromDto(ProductDto dto, @MappingTarget ProductEntity entity);
}
