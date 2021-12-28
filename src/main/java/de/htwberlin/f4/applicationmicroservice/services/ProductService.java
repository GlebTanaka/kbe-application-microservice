package de.htwberlin.f4.applicationmicroservice.services;

import de.htwberlin.f4.applicationmicroservice.dao.ProductRepository;
import de.htwberlin.f4.applicationmicroservice.models.Product;
import de.htwberlin.f4.applicationmicroservice.models.SimpleProduct;

import de.htwberlin.f4.applicationmicroservice.services.storage.StorageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
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
    private final GoogleMapsService googleMapsService;
    private final StorageService storageService;

    /**
     * Konstruktor mit Parameter
     *
     * @param productRepository ProductRepository das DAO
     * @param calculatorService
     * @param googleMapsService
     * @param storageService
     */
    @Autowired
    public ProductService(ProductRepository productRepository, CalculatorService calculatorService,
            GoogleMapsService googleMapsService, StorageService storageService) {
        this.productRepository = productRepository;
        this.calculatorService = calculatorService;
        this.googleMapsService = googleMapsService;
        this.storageService = storageService;
    }

    /**
     * Methode um alles Produkte vom DAO zu erfragen
     *
     * @return List<Product> Liste aller Produkte
     */
    public List<SimpleProduct> getProducts() {
        return productRepository.findAllSimpleProducts();
    }

    /**
     * Liefert alle Produkte mit allen Attributen auch den externen
     * 
     * @return List<Product> Liste aller Produkte mit allen gespeicherten Attributen
     */
    public List<Product> listAll() {
        List<Product> allProducts = productRepository.findAll();
        allProducts.forEach(product -> fillProduct(product));
        return allProducts;
    }

    public void fillProduct(Product product) {
        StorageObject storageObject;
        try {
            storageObject = storageService.getStorage(product.getId());
            product.setMehrwertsteuer(
                    calculatorService
                            .getMehrwertsteuer(
                                    product.getPrice()));

            product.setPlace(storageObject.getPlace());
            // Variables to calculate delivery date
            Integer interval = storageObject.getDuration();
            LocalDateTime start = LocalDateTime.now();
            product.setDeliveryDate(start.plus(Duration.ofDays(interval)));

            product.setAmount(storageObject.getAmount());
            product.setFormattedAddress(
                    googleMapsService
                            .getGeocode(product.getPlace())
                            .getResults().get(0)
                            .getFormattedAddress());
        } catch (IOException e) {}
    }

    /**
     * Methode um ein spezifischen Produkt vom DAO zu erfragen
     *
     * @param uuid UUID Suchparameter
     * @return Product das erfagte Product
     * @throws NoSuchElementException wenn kein Produkt mit der id uebereinstimmt
     */
    public Product getProduct(UUID uuid) throws NoSuchElementException {
        Product product = productRepository.findById(uuid).orElseThrow();
        fillProduct(product);
        return product;
    }
}
