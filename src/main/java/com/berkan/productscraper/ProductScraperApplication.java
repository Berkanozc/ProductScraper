package com.berkan.productscraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProductScraperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductScraperApplication.class, args);
    }

}
