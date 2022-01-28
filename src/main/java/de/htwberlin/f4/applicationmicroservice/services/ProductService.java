package de.htwberlin.f4.applicationmicroservice.services;

import de.htwberlin.f4.applicationmicroservice.dao.ProductRepository;
import de.htwberlin.f4.applicationmicroservice.models.googlemaps.GeocodeGeometry;
import de.htwberlin.f4.applicationmicroservice.models.googlemaps.GeocodeLocation;
import de.htwberlin.f4.applicationmicroservice.models.googlemaps.GeocodeObject;
import de.htwberlin.f4.applicationmicroservice.models.googlemaps.GeocodeResult;
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
 * Enable assess to information about Product object
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
        allProducts.forEach(this::fillProduct);
        return allProducts;
    }

    public void fillProduct(Product product) {
        addMehrwertsteuerToProduct(product);
        addStorageObjectToProduct(product);
        addGeocodeResultToProduct(product);
    }

    /* =============================== helper functions ======================================= */

    private void addGeocodeResultToProduct(Product product) {
        GeocodeResult geocodeResult = getGeocodeResult(product);
        if (geocodeResult != null) {
            addGeocodeResultToProduct(geocodeResult, product);
        }
    }

    private void addGeocodeResultToProduct(GeocodeResult geocodeResult, Product product) {
        addFormattedAddress(geocodeResult, product);
        addLatidute(geocodeResult, product);
        addLongitude(geocodeResult, product);
    }

    private void addFormattedAddress(GeocodeResult geocodeResult, Product product) {
        product.setFormattedAddress(getFormattedAdress(geocodeResult, product));
    }

    private void addLatidute(GeocodeResult geocodeResult, Product product) {
        product.setLat(getLatitude(geocodeResult, product));
    }

    private void addLongitude(GeocodeResult geocodeResult, Product product) {
        product.setLng(getLongitude(geocodeResult, product));
    }

    private GeocodeResult getGeocodeResult(Product product) {
        try {
            return googleMapsService.getGeocode(product.getPlace());
        } catch (IOException e) {
            return null;
        }
    }

    private GeocodeObject getGeocodeObject(GeocodeResult geocodeResult, Product product) {
        return geocodeResult
                .getResults().get(0);
    }

    private GeocodeGeometry getGeocodeGeometry(GeocodeResult geocodeResult, Product product) {
        return getGeocodeObject(geocodeResult, product).getGeometry();
    }

    private GeocodeLocation getGeocodeLocation(GeocodeResult geocodeResult, Product product) {
        return getGeocodeGeometry(geocodeResult, product).getGeocodeLocation();
    }

    private String getLongitude(GeocodeResult geocodeResult, Product product) {
        return getGeocodeLocation(geocodeResult, product).getLongitude();
    }

    private String getLatitude(GeocodeResult geocodeResult, Product product) {
        return getGeocodeLocation(geocodeResult, product).getLatitude();
    }

    private String getFormattedAdress(GeocodeResult geocodeResult, Product product) {
        return getGeocodeObject(geocodeResult, product).getFormattedAddress();
    }

    private Double getMehrwertSteuer(Product product) {
        return calculatorService.getMehrwertsteuer(product.getPrice());
    }

    private void addMehrwertsteuerToProduct(Product product) {
        product.setMehrwertsteuer(getMehrwertSteuer(product));
    }

    private void addStorageObjectToProduct(Product product) {
        StorageObject storageObject = getStorageObject(product);
        if (storageObject != null) {
            addStorageObjectToProduct(storageObject, product);
        }
    }

    private void addStorageObjectToProduct(StorageObject storageObject, Product product) {
        product.setPlace(storageObject.getPlace());
        product.setDeliveryTime(storageObject.getDuration());
        product.setDeliveryDate(Date.valueOf(LocalDate.now().plusDays(storageObject.getDuration())));
        product.setAmount(storageObject.getAmount());
    }

    private StorageObject getStorageObject(Product product) {
        try {
            return storageService.getStorage(product.getId());
        } catch (IOException e) {
        }
        return null;
    }

    public Product getProduct(UUID uuid) throws NoSuchElementException {
        Product product = productRepository.findById(uuid).orElseThrow();
        fillProduct(product);
        return product;
    }
}
