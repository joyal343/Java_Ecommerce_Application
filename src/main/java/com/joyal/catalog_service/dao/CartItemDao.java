package com.joyal.catalog_service.dao;

import com.joyal.catalog_service.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemDao extends JpaRepository<CartItem, UUID> {

    boolean existsByCartIdAndSkuId(UUID cartId, UUID skuId);
}
