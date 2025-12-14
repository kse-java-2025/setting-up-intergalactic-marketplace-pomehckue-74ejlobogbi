package com.cosmocats.marketplace.service.impl;

import com.cosmocats.marketplace.dto.OrderDto;
import com.cosmocats.marketplace.dto.OrderItemDto;
import com.cosmocats.marketplace.mapper.OrderMapper;
import com.cosmocats.marketplace.repository.OrderRepository;
import com.cosmocats.marketplace.repository.ProductRepository;
import com.cosmocats.marketplace.repository.entity.OrderEntity;
import com.cosmocats.marketplace.repository.entity.OrderItemEntity;
import com.cosmocats.marketplace.repository.entity.ProductEntity;
import com.cosmocats.marketplace.service.OrderService;
import com.cosmocats.marketplace.service.exception.OrderNotFoundException;
import com.cosmocats.marketplace.service.exception.PersistenceException;
import com.cosmocats.marketplace.domain.Order;
import com.cosmocats.marketplace.service.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
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
        log.info("Creating new order for customer: {}", orderDto.getCustomerName());

        try {
            OrderEntity orderEntity = orderMapper.toEntity(orderDto);
            orderEntity.setEntries(new ArrayList<>());

            double calculatedTotal = 0.0;

            for (OrderItemDto itemDto : orderDto.getOrderItems()) {
                ProductEntity product = productRepository.findById(itemDto.getProductId())
                        .orElseThrow(() -> new ProductNotFoundException(itemDto.getProductId()));

                OrderItemEntity itemEntity = OrderItemEntity.builder()
                        .product(product)
                        .quantity(itemDto.getQuantity())
                        .priceAtPurchase(product.getPrice())
                        .order(orderEntity)
                        .build();

                orderEntity.getEntries().add(itemEntity);
                calculatedTotal += (product.getPrice() * itemDto.getQuantity());
            }

            orderEntity.setTotalAmount(calculatedTotal);

            OrderEntity savedOrder = orderRepository.save(orderEntity);
            log.info("Order with id {} created", savedOrder.getId());

            return orderMapper.toDomain(savedOrder);
        } catch (ProductNotFoundException e) {
            throw e;
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
