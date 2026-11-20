package com.joyal.catalog_service.dao;

import com.joyal.catalog_service.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartDao extends JpaRepository<Cart, UUID> {
    boolean existsByCartIdAndCustomerId(UUID cartId, UUID customerId);
}