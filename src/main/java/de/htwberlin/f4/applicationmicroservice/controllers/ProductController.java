package de.htwberlin.f4.applicationmicroservice.controllers;

import de.htwberlin.f4.applicationmicroservice.models.Product;
import de.htwberlin.f4.applicationmicroservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }
    
    @GetMapping("/{uuid}")
    public ResponseEntity<Product> getOneProduct(@PathVariable UUID uuid){
        return ResponseEntity.ok(productService.getProduct(uuid));
    }

    @GetMapping("/export")
    public void exportProduktToCSV(){
        //TODO: call export method from Service
    }
}
