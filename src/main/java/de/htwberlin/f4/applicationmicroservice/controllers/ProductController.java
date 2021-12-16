package de.htwberlin.f4.applicationmicroservice.controllers;

import de.htwberlin.f4.applicationmicroservice.Product;
import de.htwberlin.f4.applicationmicroservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();

    }
}
