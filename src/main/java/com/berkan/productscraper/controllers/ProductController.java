package com.berkan.productscraper.controllers;

import com.berkan.productscraper.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final String ASC_SORT_TYPE = "ASC";
    private static final String DESC_SORT_TYPE = "DESC";

    @PersistenceContext
    private EntityManager em;

    /**
     * This function returns all the products to the endpoint.
     *
     * @return All products in database
     */
    @GetMapping()
    public ResponseEntity<Object> getAllProducts(
            @RequestParam(required = false, name = "brand_name") String brandName,
            @RequestParam(required = false, name = "product_name") String productName,
            @RequestParam(required = false, name = "sort") String givenSortType) {

        // Create the query name based on the given sort type
        Query q = em.createNamedQuery(determineQueryName(givenSortType));

        // Set the parameters
        q.setParameter(1, replaceEmptyVariableWithEmptyString(brandName));
        q.setParameter(2, replaceEmptyVariableWithEmptyString(productName));

        // Fetch the results from the database
        List<Product> foundProducts = q.getResultList();

        if (foundProducts.size() == 0) {
            // When there are no products found make return a message for it
            return ResponseEntity.ok("No products found with your filters");
        }

        // Return found products with a 200 ok status code
        return ResponseEntity.ok(foundProducts);
    }

    /**
     * Check what named query to use based on givenSortType
     *
     * @param givenSortType based on this variable determine the name of the named query
     * @return name of the named query
     */
    private String determineQueryName(String givenSortType) {
        // Default sorting is descending
        String sortType = DESC_SORT_TYPE.toLowerCase(Locale.ROOT);

        // Check what the given sort type is
        if (givenSortType != null && givenSortType.toUpperCase(Locale.ROOT).equals(ASC_SORT_TYPE)) {
            sortType = ASC_SORT_TYPE.toLowerCase(Locale.ROOT);
        }

        // Create the query name based on the given sort type
        return String.format("Product.filter_and_sort_%s_products", sortType);
    }

    /**
     * Returns the given parameter or an empty string based on the givenParameter.
     *
     * @param givenParameter when not null this variable is returned
     * @return when null return empty string
     */
    private String replaceEmptyVariableWithEmptyString(String givenParameter) {
        if (givenParameter == null) {
            return "";
        }

        return givenParameter;
    }
}
