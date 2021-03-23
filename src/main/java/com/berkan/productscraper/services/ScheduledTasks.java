package com.berkan.productscraper.services;

import com.berkan.productscraper.models.Product;
import com.berkan.productscraper.models.WebStore;
import com.berkan.productscraper.repositories.ProductRepository;
import com.berkan.productscraper.repositories.WebStoreRepository;
import com.berkan.productscraper.services.excel.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {

    private final int FIXED_RATE_SCRAPERS = 3600000;

    @Autowired
    private WebStoreRepository webStoreRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Scrape the web stores that are stored in the database and save the scraped products in the database.
     */
    @Scheduled(fixedRate = FIXED_RATE_SCRAPERS)
    private void runScrapers() throws IOException, InterruptedException {
        // Update the web stores with most recent added web stores into the Excel file
        readScrapeInformation();
        List<Product> fetchedProducts = new ArrayList<>();

        // Run web scraper
        WebScraper webScraper = new WebScraper();

        // Scrape for each web store if there status is active
        for (WebStore webStore : webStoreRepository.findAll()) {
            if (webStore.isActive()) {
                webScraper.setWebStore(webStore);
                productRepository.saveAll(webScraper.scrapeProducts());
            }
        }

    }

    /**
     * Save new entries in excel file to database every hour.
     */
    private void readScrapeInformation() throws IOException {
        // Read excel file and save all to database
        webStoreRepository.saveAll(
                ExcelReader.readWebStoreFromExcelFile("classpath:static/WebStores.xlsx")
        );
    }
}
