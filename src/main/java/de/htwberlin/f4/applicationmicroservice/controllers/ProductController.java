package de.htwberlin.f4.applicationmicroservice.controllers;

import de.htwberlin.f4.applicationmicroservice.models.product.Product;
import de.htwberlin.f4.applicationmicroservice.models.product.SimpleProduct;
import de.htwberlin.f4.applicationmicroservice.services.CSVService;
import de.htwberlin.f4.applicationmicroservice.services.ProductService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final CSVService csvService;

    @Autowired
    public ProductController(ProductService productService, CSVService csvService) {
        this.productService = productService;
        this.csvService = csvService;
    }

    @GetMapping
    public ResponseEntity<List<SimpleProduct>> getAllProducts() {
        return ResponseEntity.ok(productService.getSimpleProducts());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Product> getOneProduct(@PathVariable @NotNull UUID uuid) throws IOException {
        return ResponseEntity.ok(productService.getProduct(uuid));
    }

    @GetMapping("/export")
    public void exportProduktToCSV() throws IOException {
        csvService.exportProduct();
    }
}
