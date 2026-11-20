package com.joyal.catalog_service.controller;

import com.joyal.catalog_service.model.User;
import com.joyal.catalog_service.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public record UserResponse(UUID customerId, String customerName) {}

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return accountService.getAllUsers().stream()
                .map((User u) -> new UserResponse(u.getCustomerId(), u.getCustomerName()))
                .toList();
    }
}
