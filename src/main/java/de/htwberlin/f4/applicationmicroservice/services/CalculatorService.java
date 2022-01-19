package de.htwberlin.f4.applicationmicroservice.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CalculatorService {
    public Double getMehrwertsteuer(double price) {
        final String uri = "http://localhost:8081/api/v1/calculator/calculatemehrwertsteuer?preis=" + price;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Double.class);
    }
}
