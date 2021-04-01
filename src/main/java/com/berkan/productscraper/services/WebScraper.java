package com.berkan.productscraper.services;

import com.berkan.productscraper.models.Brand;
import com.berkan.productscraper.models.Image;
import com.berkan.productscraper.models.Product;
import com.berkan.productscraper.models.WebStore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WebScraper {

    private WebDriver driver;
    private WebStore webStore;

    public WebScraper() {
        // Initialize WebDriver
        setDriver(new HtmlUnitDriver());
    }

    /**
     * Scrapes products from website
     *
     * @return all found products from the website
     */
    public List<Product> scrapeProducts() {

        // Create empty list for the products that are going to be fetched
        List<Product> foundProducts = new ArrayList<>();

        // Open the url
        getDriver().get(getWebStore().getURL());

        // Locate the overview of all products
        System.out.println(getWebStore().getURL());
        WebElement productsOverview = getDriver().findElement(By.cssSelector(getWebStore().getProductsOverviewSelector()));

        // Get all products per class
        List<WebElement> products = productsOverview.findElements(By.className(getWebStore().getProductSelector().substring(1)));

        // Create a new webScraper
        WebScraper productPage = new WebScraper();
        productPage.setDriver(new HtmlUnitDriver());

        // Loop thru all found products and scrape information that is needed
        for (WebElement product : products) {
            Product newProduct = new Product();

            try {
                // Set up the information that is already visible
                newProduct.setBrand(new Brand(webStore.getBrand()));

                // Find the redirect link
                newProduct.setRedirectURL(
                        product.findElement(By.cssSelector(webStore.getProductURLSelector())).getAttribute("href")
                );

                foundProducts.add(newProduct);
            } catch (Exception e) {
                // Show error message when something goes wrong
                System.out.printf("Something went wrong while fetching the products of the brand: %s\n", getWebStore().getBrand());
                e.printStackTrace();
            }
        }

        // Add additional data to the products by visiting their individual page
        for (Product foundProduct : foundProducts) {
            try {
                scrapeAdditionalDataOfProduct(foundProduct);
            } catch (Exception e) {
                System.out.printf("Something went wrong while fetching the products of the brand: %s\n", getWebStore().getBrand());
            }
        }

        for (int i = 0; i < foundProducts.size(); i++) {
            if (foundProducts.get(i).getName() == null) {
                foundProducts.remove(foundProducts.get(i));
            }
        }

        return foundProducts;
    }

    /**
     * Scrapes information of the products from their own page
     *
     * @param newProduct with filled in data from the product it's own page
     */
    private void scrapeAdditionalDataOfProduct(Product newProduct) {
        // Navigate to the product page
        getDriver().get(newProduct.getRedirectURL());

        newProduct.setName(getDriver().findElement(By.cssSelector(getWebStore().getProductNameSelector())).getText());

        if (getDriver().findElements(By.cssSelector(getWebStore().getProductSalePriceSelector())).size() != 0) {
            String salePrice = getDriver()
                    .findElement(By.cssSelector(getWebStore()
                            .getProductSalePriceSelector()))
                    .getText()
                    .replace("€", "")
                    .replace(",", ".");

            if (salePrice.contains("$")) {
                double temp = Double.parseDouble(salePrice.replace("$", "")) / 0.85;
                salePrice = Double.toString(temp);
            }

            newProduct.setSalePrice(
                    Double.parseDouble(
                            salePrice
                    )
            );
        }

        String standardPrice = getDriver()
                .findElement(By.cssSelector(getWebStore()
                        .getProductStandardPriceSelector()))
                .getText()
                .replace("€", "")
                .replace(",", ".");

        if (standardPrice.contains("$")) {
            double temp = Double.parseDouble(standardPrice.replace("$", "")) / 0.85;
            standardPrice = Double.toString(temp);
        }

        newProduct.setStandardPrice(
                Double.parseDouble(
                        standardPrice
                )
        );

        // Get the images
        List<WebElement> imageElements = getDriver().findElements(By.cssSelector(getWebStore().getProductImageSelector().split(":")[0]));
        newProduct.setProductImages(new ArrayList<>());
        for (WebElement imageElement : imageElements) {
            String imageUrl = imageElement.getAttribute(getWebStore().getProductImageSelector().split(":")[1]);
            if (imageUrl != null) {
                if (imageUrl.startsWith("http")) {
                    newProduct.getProductImages().add(new Image(imageUrl, newProduct));
                }
            }
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebStore getWebStore() {
        return webStore;
    }

    public void setWebStore(WebStore webStore) {
        this.webStore = webStore;
    }
}
