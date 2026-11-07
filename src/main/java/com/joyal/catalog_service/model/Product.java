package com.joyal.catalog_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
public class Product {

    @Id
    private UUID id;

    private String name;

    private String description;

    private String category;

    private String brand;

    private Double price;

    private boolean isActive;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
