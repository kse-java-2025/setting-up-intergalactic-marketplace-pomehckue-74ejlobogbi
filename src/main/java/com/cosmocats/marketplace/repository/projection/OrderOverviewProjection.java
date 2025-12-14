package com.cosmocats.marketplace.repository.projection;

public interface OrderOverviewProjection {
    String getCartId();
    Double getTotalPrice();
    String getPaymentReference();
}