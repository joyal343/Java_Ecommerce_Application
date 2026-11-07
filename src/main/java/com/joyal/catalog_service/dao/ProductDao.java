package com.joyal.catalog_service.dao;

import com.joyal.catalog_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends JpaRepository<Product,Integer> {
    @Query(value = """
        SELECT * FROM product p
        WHERE (:category IS NULL OR p.category = :category)
        AND (:brand IS NULL OR p.brand = :brand)
        AND (:minprice IS NULL OR p.price >= :minprice)
        AND (:maxprice IS NULL OR p.price <= :maxprice)
        """,
            nativeQuery = true)
    List<Product> filterProducts(
            @Param("category") String category,
            @Param("brand") String brand,
            @Param("minprice") Double minprice,
            @Param("maxprice") Double maxprice
    );
}
