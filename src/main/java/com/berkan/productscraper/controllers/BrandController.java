package com.berkan.productscraper.controllers;

import com.berkan.productscraper.models.Brand;
import com.berkan.productscraper.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brands")
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;

    @GetMapping()
    public Iterable<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

}
