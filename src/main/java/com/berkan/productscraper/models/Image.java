package com.berkan.productscraper.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String url;

    @ManyToOne
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "product_name")
    private Product productName;

    public Image(String url, Product productName) {
        this.url = url;
        this.productName = productName;
    }

    public Image() {

    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Product getProductName() {
        return productName;
    }

    public void setProductName(Product productName) {
        this.productName = productName;
    }
}
