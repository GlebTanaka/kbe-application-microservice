package de.htwberlin.f4.applicationmicroservice.controllers;

import de.htwberlin.f4.applicationmicroservice.models.Product;
import de.htwberlin.f4.applicationmicroservice.models.SimpleProduct;
import de.htwberlin.f4.applicationmicroservice.services.ProductService;

import de.htwberlin.f4.applicationmicroservice.services.StorageService;
import de.htwberlin.f4.applicationmicroservice.services.storage.StorageObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Rest Controller fuer das Product
 */
@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final StorageService storageService;

    /**
     * Konstruktor mit Parameter
     * @param productService ProductService zum Aufrufen notwendiger Methoden auf dem DAO
     * @param storageService
     */
    @Autowired
    public ProductController(ProductService productService, StorageService storageService) {
        this.productService = productService;
        this.storageService = storageService;
    }

    /**
     * Erfragen nach Ausgabe aller Produkte
     * @return List<Product> Liste aller Produkte
     */
    @GetMapping
    public ResponseEntity<List<SimpleProduct>> getAllProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    /**
     * Erfragen nach einem spezifischen Produkt
     * @param uuid UUID als Suchparameter
     * @return Product welches erfagt wird
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<Product> getOneProduct(@PathVariable @NotNull UUID uuid) throws IOException {
        return ResponseEntity.ok(productService.getProduct(uuid));
    }

    /**
     * Exportiert alle Produkte mit Attributen
     */
    @GetMapping("/export")
    public void exportProduktToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv"); // text/csv;charset=ISO-8859-1
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment: filename=products_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<Product> productList = productService.listAll();
        List<StorageObject> storageObjectList = storageService.getAllStorage();

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Product ID", "Name", "Description", "Size", "Color", "Price", "Weight", "Place", "Amount", "Mehrwertsteuer", "Formatted Address", "Delivery Date"};
        String[] nameMapping = {"id", "name", "description", "size", "color", "price", "weight", "place", "amount", "mehrwertsteuer", "formattedAddress", "deliveryDate"};

        csvBeanWriter.writeHeader(csvHeader);

        for (Product product : productList) {
            //TODO hier werden alle transienten Attribute durch getProduct() aufgefuellt, geht das klueger?
            UUID uuid = product.getId();
            productService.getProduct(uuid);
            csvBeanWriter.write(product, nameMapping);
        }
        csvBeanWriter.close();
    }
}
