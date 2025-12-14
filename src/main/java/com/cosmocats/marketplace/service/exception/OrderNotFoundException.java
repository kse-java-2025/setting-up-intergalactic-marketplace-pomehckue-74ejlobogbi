package com.cosmocats.marketplace.service.exception;

public class OrderNotFoundException extends RuntimeException {
    private static final String ORDER_NOT_FOUND_MESSAGE = "Order with id %s not found";

    public OrderNotFoundException(String orderId) {
        super(String.format(ORDER_NOT_FOUND_MESSAGE, orderId));
    }
}
