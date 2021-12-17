package de.htwberlin.f4.applicationmicroservice.product.services;

import de.htwberlin.f4.applicationmicroservice.product.Product;
import de.htwberlin.f4.applicationmicroservice.product.dao.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(UUID uuid) throws NoSuchElementException {
        return productRepository.findById(uuid).orElseThrow();
    }
}

