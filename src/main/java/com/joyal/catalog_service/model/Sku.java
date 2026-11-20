package com.joyal.catalog_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

/**
 * Minimal read-only mapping of the existing sku table.
 * Extend it (or replace it with your full Sku entity) as needed.
 */
@Entity
@Table(name = "sku")
public class Sku {

    @Id
    private UUID id;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    protected Sku() {}

    public UUID getId() { return id; }
    public boolean isActive() { return active; }
}
