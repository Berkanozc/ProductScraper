package com.berkan.productscraper.repositories;

import com.berkan.productscraper.models.Brand;
import org.springframework.data.repository.CrudRepository;

public interface BrandRepository extends CrudRepository<Brand, String> {
}
