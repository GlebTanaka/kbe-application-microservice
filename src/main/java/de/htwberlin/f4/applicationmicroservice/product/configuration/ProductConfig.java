package de.htwberlin.f4.applicationmicroservice.product.configuration;

import de.htwberlin.f4.applicationmicroservice.product.Product;
import de.htwberlin.f4.applicationmicroservice.product.dao.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ProductConfig {

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            Product product01 = new Product(
                    "T-Shirt",
                    "langaermlig",
                    "M",
                    "Weiß",
                    100.0,
                    10.0
            );

            Product product02 = new Product(
                    "Hose",
                    "kurz",
                    "S",
                    "Schwarz",
                    200.0,
                    15.0
            );

            productRepository.saveAll(
                    List.of(product01,product02)
            );
        };
    }
}
