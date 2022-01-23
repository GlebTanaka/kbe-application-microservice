package de.htwberlin.f4.applicationmicroservice.controllers;

import de.htwberlin.f4.applicationmicroservice.models.product.Product;
import de.htwberlin.f4.applicationmicroservice.models.product.SimpleProduct;
import de.htwberlin.f4.applicationmicroservice.services.CSVService;
import de.htwberlin.f4.applicationmicroservice.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get Product List", description = "A List of all Products containing the name and ID")
    public ResponseEntity<List<SimpleProduct>> getAllProducts() {
        return ResponseEntity.ok(productService.getSimpleProducts());
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get Product Information", description = "Provide a matching UUID to retrieve Information of the Product")
    public ResponseEntity<Product> getOneProduct(@PathVariable @NotNull UUID uuid) throws IOException {
        return ResponseEntity.ok(productService.getProduct(uuid));
    }

    @GetMapping("/export")
    @Operation(summary = "Export Product Information to CSV", description = "Write all available data from Products into a CSV file")
    public void exportProduktToCSV() throws IOException {
        csvService.exportProduct();
    }
}
