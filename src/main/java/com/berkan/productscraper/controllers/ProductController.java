package com.berkan.productscraper.controllers;

import com.berkan.productscraper.models.Product;
import javassist.NotFoundException;
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

        // Default sorting is descending
        String sortType = DESC_SORT_TYPE.toLowerCase(Locale.ROOT);

        if (givenSortType != null && givenSortType.toUpperCase(Locale.ROOT).equals(ASC_SORT_TYPE)) {
            sortType = ASC_SORT_TYPE.toLowerCase(Locale.ROOT);
        }

        String queryName = String.format("Product.filter_and_sort_%s_products", sortType);

        Query q = em.createNamedQuery(queryName);
        if (brandName == null) {
            brandName = "";
        }

        if (productName == null) {
            productName = "";
        }

        q.setParameter(1, brandName);
        q.setParameter(2, productName);

        List<Product> foundProducts = q.getResultList();

        if (foundProducts.size() == 0) {
            return ResponseEntity.ok("No products found with your filters");
        }

        return ResponseEntity.ok(foundProducts);
    }

}
