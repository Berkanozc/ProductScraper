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

}
