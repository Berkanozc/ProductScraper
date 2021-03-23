package com.berkan.productscraper.services;

import com.berkan.productscraper.repositories.WebStoreRepository;
import com.berkan.productscraper.services.excel.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ScheduledTasks {

    private final int FIXED_RATE_SCRAPERS = 3600000;

    @Autowired
    private WebStoreRepository webStoreRepository;

    /**
     * Save new entries in excel file to database every hour.
     */
    @Scheduled(fixedRate = FIXED_RATE_SCRAPERS)
    public void readScrapeInformation() throws IOException {
        // Read excel file and save all to database
        webStoreRepository.saveAll(
                ExcelReader.readWebStoreFromExcelFile("classpath:static/WebSelectors.xlsx")
        );
    }

}
