package com.cosmocats.marketplace.repository.entity;

import jakarta.persistence.*;

import java.util.UUID;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq")
    Long id;

    String name;
    String description;
    Double price;
    String currency;
    Integer stock;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = "category_id", nullable = false)
    CategoryEntity category;

    @NaturalId
    @Column(nullable = false, unique = true)
    UUID productReference;
}