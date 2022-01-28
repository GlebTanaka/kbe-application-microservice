package de.htwberlin.f4.applicationmicroservice.services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.htwberlin.f4.applicationmicroservice.models.product.Product;

/**
 * Class to write Product Information into a CSV-file
 */
@Service
public class CSVService {

    private final ProductService productService;

    @Autowired
    public CSVService(ProductService productService) {
        this.productService = productService;
    }

    public void exportProduct() throws IOException{
        List<Product> products = productService.getProducts();
        handleFileWriter(products);
    }

    private void handleFileWriter(List<Product> products) throws IOException{
        Writer writer = new FileWriter("products.csv");
        handleCSVWriter(products, writer);
        writer.close();
    }

    private void handleCSVWriter(List<Product> products, Writer writer) throws IOException{
        CSVWriter csvWriter = new CSVWriter(writer);
        writeInCSV(products, writer);
        csvWriter.close();
    }

    private void writeInCSV(List<Product> products, Writer writer){
        StatefulBeanToCsv<Product> beanToCsv = new StatefulBeanToCsvBuilder<Product>(writer).build();
        try {
            beanToCsv.write(products);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {}
    }
}
