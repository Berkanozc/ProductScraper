package com.berkan.productscraper.repositories;

import com.berkan.productscraper.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, String> {
}
