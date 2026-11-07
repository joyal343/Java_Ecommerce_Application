package com.joyal.catalog_service.dao;

import com.joyal.catalog_service.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandDao extends JpaRepository<Brand,Integer> {
}
