package com.berkan.productscraper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Product.filter_and_sort_desc_products", query = "SELECT p FROM Product p WHERE p.brand.name LIKE CONCAT('%',?1,'%') AND p.name LIKE CONCAT('%',?2,'%') ORDER BY p.dateAdded DESC"),
        @NamedQuery(name = "Product.filter_and_sort_asc_products", query = "SELECT p FROM Product p WHERE p.brand.name LIKE CONCAT('%',?1,'%') AND p.name LIKE CONCAT('%',?2,'%') ORDER BY p.dateAdded ASC")
})
public class Product {

    @Id
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "brand", nullable = false)
    private Brand brand;

    private double salePrice;
    private double standardPrice;
    private String redirectURL;

    private LocalDateTime dateAdded;

    @OneToMany(
            mappedBy = "productName",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<Image> productImages;

    public Product() {
        this.dateAdded = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(double standardPrice) {
        this.standardPrice = standardPrice;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<Image> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<Image> productImages) {
        this.productImages = productImages;
    }
}
