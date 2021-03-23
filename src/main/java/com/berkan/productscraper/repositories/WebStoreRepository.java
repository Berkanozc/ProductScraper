package com.berkan.productscraper.repositories;

import com.berkan.productscraper.models.WebStore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebStoreRepository extends CrudRepository<WebStore, String> {

}
