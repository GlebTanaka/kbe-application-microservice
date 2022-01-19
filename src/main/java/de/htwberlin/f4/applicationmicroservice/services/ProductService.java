package de.htwberlin.f4.applicationmicroservice.services;

import de.htwberlin.f4.applicationmicroservice.dao.ProductRepository;
import de.htwberlin.f4.applicationmicroservice.models.product.Product;
import de.htwberlin.f4.applicationmicroservice.models.product.SimpleProduct;

import de.htwberlin.f4.applicationmicroservice.models.storage.StorageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
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

    @Autowired
    public ProductService(ProductRepository productRepository, CalculatorService calculatorService,
            GoogleMapsService googleMapsService, StorageService storageService) {
        this.productRepository = productRepository;
        this.calculatorService = calculatorService;
        this.googleMapsService = googleMapsService;
        this.storageService = storageService;
    }

    public List<SimpleProduct> getSimpleProducts() {
        return productRepository.findAllSimpleProducts();
    }

    public List<Product> getProducts() {
        List<Product> allProducts = productRepository.findAll();
        allProducts.forEach(product -> fillProduct(product));
        return allProducts;
    }

    public void fillProduct(Product product) {
        addMehrwertsteuerToProduct(product);
        addStorageObjectToProduct(product);
        addFormattedAddress(product);
        addLatidute(product);
        addLongitude(product);
    }

    private void addFormattedAddress(Product product) {
        try {
            product.setFormattedAddress(
                googleMapsService
                        .getGeocode(product.getPlace())
                        .getResults().get(0)
                        .getFormattedAddress());
        } catch (IOException e) {}
    }

    private void addLatidute(Product product) {
        try {
            product.setLat(googleMapsService.getGeocode(product.getPlace())
                    .getResults().get(0)
                    .getGeometry()
                    .getGeocodeLocation()
                    .getLatitude()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addLongitude(Product product) {
        try {
            product.setLng(googleMapsService.getGeocode(product.getPlace())
                    .getResults().get(0)
                    .getGeometry()
                    .getGeocodeLocation()
                    .getLongitude()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addMehrwertsteuerToProduct(Product product){
        product.setMehrwertsteuer(calculatorService.getMehrwertsteuer(product.getPrice()));
    }

    private void addStorageObjectToProduct(Product product){
        StorageObject storageObject = getStorageObject(product);
        if(storageObject!=null){
            addStorageParametersToProduct(storageObject, product);
        }
    }

    private void addStorageParametersToProduct(StorageObject storageObject, Product product){
        product.setPlace(storageObject.getPlace());
        product.setDeliveryTime(storageObject.getDuration());
        product.setDeliveryDate(Date.valueOf(LocalDate.now().plusDays(storageObject.getDuration())));
        product.setAmount(storageObject.getAmount());
    }

    private StorageObject getStorageObject(Product product){
        try{
            return storageService.getStorage(product.getId());
        }catch (IOException e) {}
        return null;
    }

    /**
     * @throws NoSuchElementException wenn kein Produkt mit der id uebereinstimmt
     */
    public Product getProduct(UUID uuid) throws NoSuchElementException {
        Product product = productRepository.findById(uuid).orElseThrow();
        fillProduct(product);
        return product;
    }
}
