package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Category;
import com.cosmocats.marketplace.dto.CategoryDto;
import com.cosmocats.marketplace.repository.entity.CategoryEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CategoryMapper {

    @Mapping(target = "products", ignore = true)
    Category toDomain(CategoryEntity entity);

    List<Category> toDomainList(List<CategoryEntity> entities);

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "categoryReference", ignore = true)
    @Mapping(target = "id", ignore = true)
    CategoryEntity toEntity(CategoryDto dto);

    CategoryDto toDto(Category domain);

    List<CategoryDto> toDtoList(List<Category> domains);

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "categoryReference", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CategoryDto dto, @MappingTarget CategoryEntity entity);
}
