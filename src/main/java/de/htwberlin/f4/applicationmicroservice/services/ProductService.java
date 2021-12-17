package de.htwberlin.f4.applicationmicroservice.services;

import de.htwberlin.f4.applicationmicroservice.dao.ProductRepository;
import de.htwberlin.f4.applicationmicroservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Service fuer Zugriff auf DAO
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Konstruktor mit Parameter
     * @param productRepository ProductRepository das DAO
     */
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Methode um alles Produkte vom DAO zu erfragen
     * @return List<Product> Liste aller Produkte
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * Methode um ein spezifischen Produkt vom DAO zu erfragen
     * @param uuid UUID Suchparameter
     * @return Product das erfagte Product
     * @throws NoSuchElementException wenn kein Produkt mit der id uebereinstimmt
     */
    public Product getProduct(UUID uuid) throws NoSuchElementException {
        return productRepository.findById(uuid).orElseThrow();
    }
}

