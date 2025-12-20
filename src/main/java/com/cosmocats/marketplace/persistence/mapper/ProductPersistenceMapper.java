package com.cosmocats.marketplace.persistence.mapper;

import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.persistence.entity.ProductEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {CategoryPersistenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductPersistenceMapper {

    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product domain);

    List<Product> toDomainList(List<ProductEntity> entities);

    List<ProductEntity> toEntityList(List<Product> domains);
}
