package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Order;
import com.cosmocats.marketplace.dto.OrderDto;
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    Order toEntity(OrderDto dto);

    List<OrderDto> toDtoList(List<Order> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    void updateEntityFromDto(OrderDto dto, @MappingTarget Order entity);

    default String mapOrderStatus(Order.OrderStatus status) {
        return status != null ? status.name() : null;
    }

    default Order.OrderStatus mapOrderStatus(String status) {
        return status != null ? Order.OrderStatus.valueOf(status) : null;
    }
}
