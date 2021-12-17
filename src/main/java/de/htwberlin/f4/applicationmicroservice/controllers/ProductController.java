package de.htwberlin.f4.applicationmicroservice.controllers;

import de.htwberlin.f4.applicationmicroservice.models.Product;
import de.htwberlin.f4.applicationmicroservice.models.SimpleProduct;
import de.htwberlin.f4.applicationmicroservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Rest Controller fuer das Product
 */
@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {

    private final ProductService productService;

    /**
     * Konstruktor mit Parameter
     * @param productService ProductService zum Aufrufen notwendiger Methoden auf dem DAO
     */
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Erfragen nach Ausgabe aller Produkte
     * @return List<Product> Liste aller Produkte
     */
    @GetMapping
    public ResponseEntity<List<SimpleProduct>> getAllProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    /**
     * Erfragen nach einem spezifischen Produkt
     * @param uuid UUID als Suchparameter
     * @return Product welches erfagt wird
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<Product> getOneProduct(@PathVariable UUID uuid) {
        return ResponseEntity.ok(productService.getProduct(uuid));
    }

    /**
     * Exportiert alle Produkte mit Attributen
     */
    @GetMapping("/export")
    public void exportProduktToCSV() {
        //TODO: call export method from Service
    }
}
