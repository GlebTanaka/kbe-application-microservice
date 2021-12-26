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
    public ProductService(ProductRepository productRepository, CalculatorService calculatorService, GoogleMapsService googleMapsService, StorageService storageService) {
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

    //TODO eventuell unnoetig? wird nur intern vom exportProduktToCSV() genutzt
    /**
     * Liefert alle Produkte mit allen Attributen aus der Datenbank
     * @return List<Product> Liste aller Produkte mit allen gespeicherten Attributen
     */
    public List<Product> listAll() throws IOException {

        List<Product> allProducts = productRepository.findAll();
        // Okay, das ist etwas komplexer als erwartet, es langt nicht nur das Json objekt mit liste
        // von Objekten, ich brauche ja auch die merhwersteuer und google api, spaetestesn dann muss er jedes mal anfragen...
        List<StorageObject> storageObjectList = storageService.getAllStorage();
        for (Product product : allProducts) {
            for (StorageObject storageObject : storageObjectList) {
                if(product.getId().equals(storageObject.getId())){
                    product.setPlace(storageObject.getPlace());
                    product.setAmount(storageObject.getAmount());
                    product.setDeliveryDate(LocalDateTime.now().plus(Duration.ofDays(storageObject.getDuration())));

                }
            }
        }
        return allProducts;
    }

    /**
     * Methode um ein spezifischen Produkt vom DAO zu erfragen
     *
     * @param uuid UUID Suchparameter
     * @return Product das erfagte Product
     * @throws NoSuchElementException wenn kein Produkt mit der id uebereinstimmt
     */
    public Product getProduct(UUID uuid) throws NoSuchElementException, IOException {
        Product product = productRepository.findById(uuid).orElseThrow();
        product.setMehrwertsteuer(
                calculatorService
                        .getMehrwertsteuer(
                                product.getPrice()));
        //TODO Abhaengigkeit von Reihnfolge. setPlace() muss vor setFormattedAddress() kommen.
        product.setPlace(
                storageService
                        .getStorage(uuid).getPlace());

        product.setFormattedAddress(
                googleMapsService
                        .getGeocode(product.getPlace())
                        .getResults().get(0)
                        .getFormattedAddress());

        // Variables to calculate delivery date
        Integer interval = storageService.getStorage(uuid).getDuration();
        LocalDateTime start = LocalDateTime.now();
        product.setDeliveryDate(start.plus(Duration.ofDays(interval)));

        product.setAmount(storageService.getStorage(uuid).getAmount());

        return product;
    }
}

