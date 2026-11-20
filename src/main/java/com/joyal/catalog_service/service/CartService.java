package com.joyal.catalog_service.service;

import com.joyal.catalog_service.dao.CartDao;
import com.joyal.catalog_service.dao.CartItemDao;
import com.joyal.catalog_service.dao.SkuDao;
import com.joyal.catalog_service.dao.UserDao;
import com.joyal.catalog_service.model.Cart;
import com.joyal.catalog_service.model.CartItem;
import com.joyal.catalog_service.model.Sku;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class CartService {

    private final UserDao userDao;
    private final CartDao cartDao;
    private final CartItemDao cartItemDao;
    private final SkuDao skuDao;

    public CartService(UserDao userDao, CartDao cartDao, CartItemDao cartItemDao, SkuDao skuDao) {
        this.userDao = userDao;
        this.cartDao = cartDao;
        this.cartItemDao = cartItemDao;
        this.skuDao = skuDao;
    }

    public Cart createCart(UUID customerId) {
        if (!userDao.existsById(customerId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        return cartDao.save(new Cart(customerId));
    }

    public CartItem addItem(UUID customerId, UUID cartId, UUID skuId) {
        // 1. cart must exist AND belong to this customer
        if (!cartDao.existsByCartIdAndCustomerId(cartId, customerId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found for this customer");
        }

        // 2. sku must exist and be active
        Sku sku = skuDao.findById(skuId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
        if (!sku.isActive()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Item is not available");
        }

        // 3. no duplicates (unique constraint on cart_id + sku_id backs this up)
        if (cartItemDao.existsByCartIdAndSkuId(cartId, skuId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Item already in cart");
        }

        return cartItemDao.save(new CartItem(cartId, skuId));
    }
}