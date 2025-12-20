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
    OrderDto toDto(Order domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    Order toDomainWithoutIdTimestampsAndItems(OrderDto dto);

    List<OrderDto> toDtoList(List<Order> domains);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    void updateDomainFromDtoWithoutIdTimestampsAndItems(OrderDto dto, @MappingTarget Order domain);

    default String mapOrderStatusToString(Order.OrderStatus status) {
        return status != null ? status.name() : null;
    }

    default Order.OrderStatus mapStringToOrderStatus(String status) {
        return status != null ? Order.OrderStatus.valueOf(status) : null;
    }
}
