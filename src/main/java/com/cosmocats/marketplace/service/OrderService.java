package com.cosmocats.marketplace.service;

public interface OrderService {

    List<Order> getAllOrders();

    Order getOrderById(String id);

    Order createOrder(OrderDto order);

    void deleteOrderById(String id);
}
