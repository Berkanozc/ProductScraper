package com.berkan.productscraper.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class WebStore {

    @Id
    private String brand;

    private String productNameSelector;
    private String URL;
    private String productsOverviewSelector;
    private String productSelector;
    private String productImageSelector;
    private String productURLSelector;
    private String productSalePriceSelector;
    private String productStandardPriceSelector;
    private boolean isActive;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductNameSelector() {
        return productNameSelector;
    }

    public void setProductNameSelector(String productNameSelector) {
        this.productNameSelector = productNameSelector;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getProductsOverviewSelector() {
        return productsOverviewSelector;
    }

    public void setProductsOverviewSelector(String productsOverviewSelector) {
        this.productsOverviewSelector = productsOverviewSelector;
    }

    public String getProductSelector() {
        return productSelector;
    }

    public void setProductSelector(String productSelector) {
        this.productSelector = productSelector;
    }

    public String getProductImageSelector() {
        return productImageSelector;
    }

    public void setProductImageSelector(String productImageSelector) {
        this.productImageSelector = productImageSelector;
    }

    public String getProductURLSelector() {
        return productURLSelector;
    }

    public void setProductURLSelector(String productURLSelector) {
        this.productURLSelector = productURLSelector;
    }

    public String getProductSalePriceSelector() {
        return productSalePriceSelector;
    }

    public void setProductSalePriceSelector(String productSalePriceSelector) {
        this.productSalePriceSelector = productSalePriceSelector;
    }

    public String getProductStandardPriceSelector() {
        return productStandardPriceSelector;
    }

    public void setProductStandardPriceSelector(String productStandardPriceSelector) {
        this.productStandardPriceSelector = productStandardPriceSelector;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
