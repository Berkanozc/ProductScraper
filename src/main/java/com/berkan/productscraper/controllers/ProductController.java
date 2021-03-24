package com.berkan.productscraper.controllers;

import com.berkan.productscraper.models.Product;
import com.berkan.productscraper.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    /**
     * This function returns all the products to the endpoint.
     *
     * @return All products in database
     */
    @GetMapping()
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

}
