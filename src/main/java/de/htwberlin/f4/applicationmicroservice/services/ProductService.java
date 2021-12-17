package de.htwberlin.f4.applicationmicroservice.services;

import de.htwberlin.f4.applicationmicroservice.dao.ProductRepository;
import de.htwberlin.f4.applicationmicroservice.models.Product;
import de.htwberlin.f4.applicationmicroservice.models.SimpleProduct;

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
    private final CalculatorService calculatorService;

    /**
     * Konstruktor mit Parameter
     * @param productRepository ProductRepository das DAO
     */
    @Autowired
    public ProductService(ProductRepository productRepository, CalculatorService calculatorService) {
        this.productRepository = productRepository;
        this.calculatorService = calculatorService;
    }

    /**
     * Methode um alles Produkte vom DAO zu erfragen
     * @return List<Product> Liste aller Produkte
     */
    public List<SimpleProduct> getProducts() {
        return productRepository.findAllSimpleProducts();
    }

    /**
     * Methode um ein spezifischen Produkt vom DAO zu erfragen
     * @param uuid UUID Suchparameter
     * @return Product das erfagte Product
     * @throws NoSuchElementException wenn kein Produkt mit der id uebereinstimmt
     */
    public Product getProduct(UUID uuid) throws NoSuchElementException {
        Product product = productRepository.findById(uuid).orElseThrow();
        product.setMehrwertsteuer(calculatorService.getMehrwertsteuer(product.getPrice()));
    }
}

