package de.htwberlin.f4.applicationmicroservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htwberlin.f4.applicationmicroservice.services.googlemaps.GeocodeResult;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Zugriff auf Addressinformation
 */
@Service
public class GoogleMapsService {

    /**
     * Liefert ein GeocodeResult Object im JSON-Format
     * @param address String, Ort des Productes
     * @return GeocodeResult Ergebnis der Anfrage
     * @throws IOException wenn es keine Resoponse gibt
     */
    public GeocodeResult getGeocode(String address) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        //TODO extract API key as env variable from source
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/geocode/json?address="
                        + encodedAddress
                        // Manually extract api key for now, need to paste it in before running
                        + "&key=")
                .get()
                .build();
        ResponseBody responseBody = client.newCall(request).execute().body();
        ObjectMapper objectMapper = new ObjectMapper();
        //TODO exception handling, if response equals null
        return objectMapper.readValue(responseBody.string(), GeocodeResult.class);
    }
}

