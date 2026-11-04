package com.joyal.catalog_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("catalog")
public class CatalogController {
    @Autowired
    CatalogService catalogService;

    @GetMapping("get-products")
    public String getAllProducts(){ // Keep this for Designing a Response
        return catalogService.getAllProducts();
    }
}
