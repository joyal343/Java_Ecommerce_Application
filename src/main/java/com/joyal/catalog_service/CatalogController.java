package com.joyal.catalog_service;

import com.joyal.catalog_service.model.Brand;
import com.joyal.catalog_service.model.Category;
import com.joyal.catalog_service.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("catalog")
public class CatalogController {
    @Autowired
    CatalogService catalogService;

    @GetMapping("product")
    public String getAllProducts(){ // Keep this for Designing a Response
        return catalogService.getAllProducts();
    }

    // controller
    // product/filter?category=<cat>&brand=<b>&minprice=<mp>&maxprice=<maxp>>>
    // Is there any way to indicate not provided ?
    // I want to pass it to a service layer function
    // I want to ultimately create a query with a where clause with the above variables as optional
    // what are your suggestions
    @GetMapping("product/filter")
    public ResponseEntity<List<Product>> getFilteredProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
            ){
        return catalogService.getFilteredProducts(category,brand,minPrice,maxPrice);
    }

    @GetMapping("product/category")
    public ResponseEntity<List<Category>> getCategories(){
        return catalogService.getCategories();
    }

    @GetMapping("product/brand")
    public ResponseEntity<List<Brand>> getBrands(){
        return catalogService.getBrands();
    }
}
