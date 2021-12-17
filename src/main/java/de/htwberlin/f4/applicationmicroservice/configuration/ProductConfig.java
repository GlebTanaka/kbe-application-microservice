package de.htwberlin.f4.applicationmicroservice.configuration;

import de.htwberlin.f4.applicationmicroservice.models.Product;
import de.htwberlin.f4.applicationmicroservice.dao.ProductRepository;
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
                    "Angefertig aus 100 % Baumwolle für den Sommer.",
                    "L",
                    "Türkis",
                    50.00,
                    0.20
            );

            Product product02 = new Product(
                    "Jeans",
                    "Aus Denim, ein strapazierfähiges und haltbares Baumwollgewebe.",
                    "S",
                    "Blau",
                    120.00,
                    1.00
            );

            Product product03 = new Product(
                    "Pullover",
                    "Aus 100 % Schurwolle, hält im Winter warm.",
                    "M",
                    "Orange",
                    199.90,
                    0.50
            );

            Product product04 = new Product(
                    "Flip-Flops",
                    "Luftig und liecht, perfekt fuer den Strand.",
                    "38",
                    "Pink",
                    30.00,
                    0.20
            );

            Product product05 = new Product(
                    "Jacke",
                    "Daunenjacken, hälte warm, selbst bei eisigen Temperaturen.",
                    "XXL",
                    "Gelb",
                    340.00,
                    2.50
            );

            Product product06 = new Product(
                    "Hut",
                    "Geflochten von unabhängigen Hut-Webern.",
                    "XL",
                    "Grün",
                    250.00,
                    0.10
            );

            Product product07 = new Product(
                    "Kleid",
                    "Traegerlos, aus Seide.",
                    "XS",
                    "Rot",
                    420.00,
                    0.20
            );

            Product product08 = new Product(
                    "Socken",
                    "Halbsocken fuer flache Schue.",
                    "40-42",
                    "Lila",
                    12.90,
                    0.05
            );

            Product product09 = new Product(
                    "Hemd",
                    "Perfekt für jeden festlichen Anlass!",
                    "XXXL",
                    "Weiß",
                    70.00,
                    0.30
            );

            Product product10 = new Product(
                    "Turnschuhe",
                    "Barefoot-Style, um die Fuesse gesund zu halten.",
                    "45",
                    "Schwarz",
                    90.00,
                    0.30
            );

            productRepository.saveAll(
                    List.of(
                            product01,
                            product02,
                            product03,
                            product04,
                            product05,
                            product06,
                            product07,
                            product08,
                            product09,
                            product10
                    )
            );
        };
    }
}
