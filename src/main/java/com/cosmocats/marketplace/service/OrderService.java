package com.cosmocats.marketplace.service;

import com.cosmocats.marketplace.domain.Order;
import com.cosmocats.marketplace.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    Order getOrderById(String id);

    Order createOrder(OrderDto order);

    void deleteOrderById(String id);
}
