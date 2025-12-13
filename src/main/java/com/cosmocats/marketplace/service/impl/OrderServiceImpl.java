package com.cosmocats.marketplace.service.impl;

import com.cosmocats.marketplace.dto.OrderDto;
import com.cosmocats.marketplace.mapper.OrderMapper;
import com.cosmocats.marketplace.repository.OrderRepository;
import com.cosmocats.marketplace.repository.entity.OrderEntity;
import com.cosmocats.marketplace.service.OrderService;
import com.cosmocats.marketplace.service.exception.OrderNotFoundException;
import com.cosmocats.marketplace.service.exception.PersistenceException;
import com.cosmocats.marketplace.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderMapper.toDomainList(orderRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(String orderId) {
        OrderEntity order = orderRepository.findByNaturalId(orderId).orElseThrow(() -> {
            log.info("Order with id {} not found", orderId);
            return new OrderNotFoundException(orderId);
        });

        return orderMapper.toDomain(order);
    }

    @Override
    @Transactional
    public Order createOrder(OrderDto orderDto) {
        log.info("Creating new order: {}", orderDto.getId());

        try {
            Order order = orderMapper.toDomain(orderRepository.save(orderMapper.toEntity(orderDto)));
            log.info("Order with id {} created", order.getId());
            return order;
        } catch (Exception ex) {
            log.error("Exception occurred while saving order");
            throw new PersistenceException(ex);
        }
    }

    @Override
    @Transactional
    public void deleteOrderById(String id) {
        try {
            orderRepository.deleteByNaturalId(id);
            log.info("Order with id {} deleted", id);
        } catch (Exception ex) {
            log.error("Exception occurred while deleting order with id {}", id);
            throw new PersistenceException(ex);
        }
    }
}
