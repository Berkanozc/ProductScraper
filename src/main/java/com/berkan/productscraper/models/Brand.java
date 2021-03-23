package com.berkan.productscraper.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Brand {

    @Id
    private String name;

    @OneToMany(
            mappedBy = "brand"
    )
    @JsonBackReference
    private List<Product> products;


    public Brand(String name) {
        this.name = name;
    }

    public Brand() {

    }
}
