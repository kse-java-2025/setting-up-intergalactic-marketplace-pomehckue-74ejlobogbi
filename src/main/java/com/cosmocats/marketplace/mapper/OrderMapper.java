package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Order;
import com.cosmocats.marketplace.dto.OrderDto;
import com.cosmocats.marketplace.repository.entity.OrderEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {OrderItemMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderMapper {

    @Mapping(source = "status", target = "status")
    OrderDto toDto(Order entity);

    @Mapping(source = "entries", target = "orderItems")
    Order toDomain(OrderEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entries", ignore = true)
    OrderEntity toEntity(OrderDto dto);

    List<OrderDto> toDtoList(List<Order> domains);

    List<Order> toDomainList(List<OrderEntity> entities);

    default String mapOrderStatus(Order.OrderStatus status) {
        return status != null ? status.name() : null;
    }

    default Order.OrderStatus mapOrderStatus(String status) {
        return status != null ? Order.OrderStatus.valueOf(status) : null;
    }
}
