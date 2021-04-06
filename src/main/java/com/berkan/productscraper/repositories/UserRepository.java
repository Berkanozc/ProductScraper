package com.berkan.productscraper.repositories;

import com.berkan.productscraper.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
