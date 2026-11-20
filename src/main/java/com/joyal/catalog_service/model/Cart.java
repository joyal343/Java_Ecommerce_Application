package com.joyal.catalog_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(name = "cart_id")
    private UUID cartId;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    protected Cart() {}

    public Cart(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getCartId() { return cartId; }
    public UUID getCustomerId() { return customerId; }
}
