package de.htwberlin.f4.applicationmicroservice.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Enables Access to Calculator API
 */
@Service
public class CalculatorService {
    public Double getMehrwertsteuer(double price) {
        final String uri = "http://localhost:8081/api/v1/calculator/calculatemehrwertsteuer?preis=" + price;
        RestTemplate restTemplate = new RestTemplate();
        try  {
            return restTemplate.getForObject(uri, Double.class);
        } catch (RestClientException e) {
            System.err.println(e.getMessage());
            System.err.println("Failed to connect to calculator service");
            return null;
        }
    }
}
