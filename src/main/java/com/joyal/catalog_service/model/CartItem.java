package com.joyal.catalog_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @Column(name = "cart_id", nullable = false)
    private UUID cartId;

    @Column(name = "sku_id", nullable = false)
    private UUID skuId;

    protected CartItem() {}

    public CartItem(UUID cartId, UUID skuId) {
        this.cartId = cartId;
        this.skuId = skuId;
    }

    public UUID getId() { return id; }
    public UUID getCartId() { return cartId; }
    public UUID getSkuId() { return skuId; }
}
