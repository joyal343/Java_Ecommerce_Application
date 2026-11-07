package com.joyal.catalog_service;

import com.joyal.catalog_service.dao.BrandDao;
import com.joyal.catalog_service.dao.CategoryDao;
import com.joyal.catalog_service.dao.ProductDao;
import com.joyal.catalog_service.model.Brand;
import com.joyal.catalog_service.model.Category;
import com.joyal.catalog_service.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    @Autowired
    ProductDao productDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    BrandDao brandDao;

    public String getAllProducts() {
        return "Hello  from the depths of logic";
    }

    public ResponseEntity<List<Product>> getFilteredProducts(String category, String brand, Double minPrice, Double maxPrice) {
        List<Product> products = productDao.filterProducts(category,brand,minPrice,maxPrice);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryDao.findAll();
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    public ResponseEntity<List<Brand>> getBrands() {
        List<Brand> brands = brandDao.findAll();
        return new ResponseEntity<>(brands,HttpStatus.OK);
    }
}
