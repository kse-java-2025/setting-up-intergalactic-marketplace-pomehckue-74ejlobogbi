package com.cosmocats.marketplace.repository;

import com.cosmocats.marketplace.repository.entity.OrderEntity;
import com.cosmocats.marketplace.repository.projection.OrderOverviewProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends NaturalIdRepository<OrderEntity, String> {

    @Query("""
    SELECT o.cartId AS cartId,
           o.totalPrice AS totalPrice,
           o.paymentReference AS paymentReference
    FROM OrderEntity o
    ORDER BY o.totalPrice DESC
""")
    List<OrderOverviewProjection> getOrderSummaries();
}
