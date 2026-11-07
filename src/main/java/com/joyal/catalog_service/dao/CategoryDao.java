package com.joyal.catalog_service.dao;

import com.joyal.catalog_service.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category,Integer> {
}
