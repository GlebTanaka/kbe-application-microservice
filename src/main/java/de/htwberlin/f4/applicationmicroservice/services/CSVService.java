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

import de.htwberlin.f4.applicationmicroservice.models.Product;

@Service
public class CSVService {
    @Autowired
    private ProductService productService;

    public void exportProduct() throws IOException{
        /*response.setContentType("text/csv"); // text/csv;charset=ISO-8859-1
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment: filename=products_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<Product> productList = productService.listAll();

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Id", "Name", "Description", "Size", "Color", "Price", "Weight", "Place", "Amount", "Mehrwertsteuer", "FormattedAddress", "DeliveryDate"};
        String[] nameMapping = {"id", "name", "description", "size", "color", "price", "weight", "place", "amount", "mehrwertsteuer", "formattedAddress", "deliveryDate"};

        csvBeanWriter.writeHeader(csvHeader);

        for (Product product : productList) {
            csvBeanWriter.write(product, nameMapping);
        }
        csvBeanWriter.close();*/
        List<Product> productList = productService.listAll();

        Writer writer = new FileWriter("products.csv");
        CSVWriter csvWriter = new CSVWriter(writer);
        StatefulBeanToCsv<Product> beanToCsv = new StatefulBeanToCsvBuilder<Product>(writer).build();
        try {
            beanToCsv.write(productList);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            //e.printStackTrace();
        }
       
        csvWriter.close();
    }
}
