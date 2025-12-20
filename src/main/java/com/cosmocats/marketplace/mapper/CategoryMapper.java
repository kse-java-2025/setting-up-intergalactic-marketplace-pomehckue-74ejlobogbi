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

    CategoryDto toDto(Category domain);

    @Mapping(target = "products", ignore = true)
    Category toDomainWithoutProducts(CategoryDto dto);

    List<CategoryDto> toDtoList(List<Category> domains);

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateDomainFromDtoWithoutIdAndProducts(CategoryDto dto, @MappingTarget Category domain);
}
