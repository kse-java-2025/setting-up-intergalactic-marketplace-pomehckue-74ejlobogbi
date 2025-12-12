package com.cosmocats.marketplace.service.impl;

import com.cosmocats.marketplace.repository.OrderRepository;
import com.cosmocats.marketplace.repository.ProductRepository;
import com.cosmocats.marketplace.repository.entity.CategoryEntity;
import com.cosmocats.marketplace.repository.entity.OrderEntity;
import com.cosmocats.marketplace.repository.entity.OrderEntryEntity;
import com.cosmocats.marketplace.repository.entity.ProductEntity;
import com.cosmocats.marketplace.service.OrderService;
import com.cosmocats.marketplace.service.exception.CategoryNotFoundException;
import com.cosmocats.marketplace.service.exception.OrderNotFoundException;
import com.cosmocats.marketplace.service.exception.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderMapper.toOrderList(orderRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(String orderId) {
        OrderEntity order = orderRepository.findByNaturalId(orderId).orElseThrow(() -> {
            log.info("Order with id {} not found", orderId);
            return new OrderNotFoundException(orderId);
        });

        return orderMapper.toOrder(category);
    }

    @Override
    @Transactional
    public Order createOrder(OrderDto orderDto) {
        try {
            OrderEntity order = orderMapper.toOrder(dto);
            order.setCartId(UUID.randomUUID().toString());

            orderDto.getEntries().forEach(entryDto -> {
                OrderEntryEntity entry = new OrderEntryEntity();

                ProductEntity product = productRepository.findById(entryDto.getProductId())
                        .orElseThrow(() -> new ProductNotFoundException(entryDto.getProductId()));

                entry.setProduct(product);
                entry.setQuantity(entryDto.getQuantity());
                entry.setOrder(order);

                order.getEntries().add(entry);
            });

            double total = order.getEntries().stream()
                    .mapToDouble(e -> e.getProduct().getPrice() * e.getQuantity())
                    .sum();
            order.setTotalPrice(total);

            OrderEntity saved = orderRepository.save(order);
            log.info("Order with naturalId {} created", saved.getCartId());

            return orderMapper.toDomain(saved);
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
            log.info("Category with id {} deleted", id);
        } catch (Exception ex) {
            log.error("Exception occurred while deleting category with id {}", id);
            throw new PersistenceException(ex);
        }
    }
}