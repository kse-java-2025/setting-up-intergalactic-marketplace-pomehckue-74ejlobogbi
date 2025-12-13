package com.cosmocats.marketplace.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_id_seq")
    @SequenceGenerator(name = "order_item_id_seq", sequenceName = "order_item_id_seq")
    Long id;

    Integer quantity;
    Double price;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = "product", referencedColumnName = "id", nullable = false)
    ProductEntity product;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = "order", referencedColumnName = "id", nullable = false)
    OrderEntity order;
}