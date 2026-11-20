package com.joyal.catalog_service.controller;

import com.joyal.catalog_service.model.Cart;
import com.joyal.catalog_service.service.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // ---- request / response DTOs ----
    public record CreateCartRequest(@NotNull UUID customerId) {}

    public record AddItemRequest(@NotNull UUID customerId,
                                 @NotNull UUID cartId,
                                 @NotNull UUID itemId) {}   // itemId = sku id

    public record CartResponse(UUID customerId, UUID cartId) {}

    public record CartItemResponse(UUID cartId, UUID itemId) {}

    // ---- endpoints ----

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse create(@Valid @RequestBody CreateCartRequest request) {
        Cart cart = cartService.createCart(request.customerId());
        return new CartResponse(cart.getCustomerId(), cart.getCartId());
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemResponse add(@Valid @RequestBody AddItemRequest request) {
        CartItem item = cartService.addItem(request.customerId(), request.cartId(), request.itemId());
        return new CartItemResponse(item.getCartId(), item.getSkuId());
    }
}
