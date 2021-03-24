package com.berkan.productscraper.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Brand {

    @Id
    @JsonManagedReference
    private String name;

    @OneToMany(
            mappedBy = "brand"
    )
    @JsonManagedReference
    @JsonIgnore
    private List<Product> products;

    public Brand(String name) {
        this.name = name;
    }

    public Brand() {

    }
}
