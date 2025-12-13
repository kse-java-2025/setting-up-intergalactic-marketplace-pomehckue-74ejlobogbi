package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Category;
import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.ProductDto;
import com.cosmocats.marketplace.repository.entity.ProductEntity;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product domain);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "id", ignore = true)
    Product toDomain(ProductEntity entity);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "id", ignore = true)
    ProductEntity toEntity(ProductDto dto);

    List<ProductDto> toDtoList(List<Product> dtos);

    List<Product> toDomainList(List<ProductEntity> entities);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ProductDto dto, @MappingTarget ProductEntity entity);

    default Category mapCategoryId(UUID categoryId) {
        if (categoryId == null) {
            return null;
        }
        Category category = new Category();
        category.setId(categoryId);
        return category;
    }
}
