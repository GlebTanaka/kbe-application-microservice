package de.htwberlin.f4.applicationmicroservice.services;

import org.springframework.web.client.RestTemplate;

public class CalculatorService {
    /**
     * Liefert die Mehrwertsteur vom Produkt
     * @return Double die errechnete Mehrwertsteuer
     */
    public Double getMehrwertsteuer(double price) {
        final String uri = "http://localhost:8081/api/v1/calculator/calculatemehrwertsteuer?preis=" + price;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Double.class);
    }
}
