package com.berkan.productscraper.services.excel;

import com.berkan.productscraper.models.WebStore;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    /**
     * Searches for the excel if found the excel file is red. Otherwise a FileNotFoundException is thrown.
     *
     * @param excelFilePath path to the excel file.
     * @return list of WebStores found in the excel file.
     * @throws IOException when no excel file is found.
     */
    public static List<WebStore> readWebStoreFromExcelFile(String excelFilePath) throws IOException {

        try (Workbook wb = WorkbookFactory.create(ResourceUtils.getFile(excelFilePath))) {
            List<WebStore> webStoreSelectorsList = go(wb);
            wb.close();
            return webStoreSelectorsList;
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException();
        }

    }

    /**
     * Reads the given workbook and converts it to the model WebStore.
     *
     * @param wb found workbook.
     * @return list of all fetched WebStores.
     */
    private static List<WebStore> go(Workbook wb) {

        List<WebStore> webStoreSelectorsList = new ArrayList<>();
        // Get the first sheet of the excel file
        Sheet sheet = wb.getSheetAt(0);

        // For each row in the sheet check each cel in the row
        for (Row row : sheet) {
            if (row.getRowNum() > 0) {
                WebStore webStoreSelectors = new WebStore();

                for (Cell cell : row) {
                    switch (cell.getColumnIndex()) {
                        case 0:
                            webStoreSelectors.setBrand(cell.getStringCellValue());
                        case 1:
                            webStoreSelectors.setProductsOverviewSelector(cell.getStringCellValue());
                            break;
                        case 2:
                            webStoreSelectors.setProductSelector(cell.getStringCellValue());
                            break;
                        case 3:
                            webStoreSelectors.setProductNameSelector(cell.getStringCellValue());
                            break;
                        case 4:
                            webStoreSelectors.setProductSalePriceSelector(cell.getStringCellValue());
                            break;
                        case 5:
                            webStoreSelectors.setProductStandardPriceSelector(cell.getStringCellValue());
                            break;
                        case 6:
                            webStoreSelectors.setProductImageSelector(cell.getStringCellValue());
                            break;
                        case 7:
                            webStoreSelectors.setProductURLSelector(cell.getStringCellValue());
                            break;
                        case 8:
                            webStoreSelectors.setURL(cell.getStringCellValue());
                            break;
                        case 9:
                            webStoreSelectors.setActive(Boolean.parseBoolean(cell.getStringCellValue()));
                            break;
                    }
                }

                if (webStoreSelectors.getBrand() != null) {
                    webStoreSelectorsList.add(webStoreSelectors);
                }
            }

        }

        return webStoreSelectorsList;
    }


}
