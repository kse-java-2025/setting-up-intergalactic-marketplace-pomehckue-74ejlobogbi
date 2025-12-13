package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Category;
import com.cosmocats.marketplace.dto.CategoryDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CategoryMapper {

    CategoryDto toDto(Category entity);

    @Mapping(target = "products", ignore = true)
    Category toEntity(CategoryDto dto);

    List<CategoryDto> toDtoList(List<Category> entities);

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CategoryDto dto, @MappingTarget Category entity);
}
