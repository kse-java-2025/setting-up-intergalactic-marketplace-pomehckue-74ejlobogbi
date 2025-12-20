package com.cosmocats.marketplace.persistence.mapper;

import com.cosmocats.marketplace.domain.Category;
import com.cosmocats.marketplace.persistence.entity.CategoryEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CategoryPersistenceMapper {

    @Mapping(target = "products", ignore = true)
    Category toDomain(CategoryEntity entity);

    @Mapping(target = "products", ignore = true)
    CategoryEntity toEntity(Category domain);

    List<Category> toDomainList(List<CategoryEntity> entities);
}
