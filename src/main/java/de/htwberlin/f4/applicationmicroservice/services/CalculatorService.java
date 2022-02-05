package de.htwberlin.f4.applicationmicroservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Enables Access to Calculator API
 */
@Service
public class CalculatorService {

    Logger logger = LoggerFactory.getLogger(CalculatorService.class);

    public Double getMehrwertsteuer(double price) {
        final String uri = "http://localhost:8081/api/v1/calculator/calculatemehrwertsteuer?preis=" + price;
        RestTemplate restTemplate = new RestTemplate();
        try  {
            return restTemplate.getForObject(uri, Double.class);
        } catch (RestClientException e) {
            logger.error("Failed to connect to Calculator Service. Unable to calculate Mehrwertstuer of Product.");
            return null;
        }
    }
}
