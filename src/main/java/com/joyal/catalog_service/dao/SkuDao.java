package com.joyal.catalog_service.dao;

import com.joyal.catalog_service.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SkuDao extends JpaRepository<Sku, UUID> {
}
