package de.htwberlin.f4.applicationmicroservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htwberlin.f4.applicationmicroservice.models.googlemaps.GeocodeResult;
import de.htwberlin.f4.applicationmicroservice.util.RequestUtil;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Accessing address information from GoogleMaps
 */
@Service
public class GoogleMapsService {

    Logger logger = LoggerFactory.getLogger(GoogleMapsService.class);

    /**
     * Method to get a GeocodeResult Object in JSON format
     */
    public GeocodeResult getGeocode(String address) throws IOException {
        if (address == null) {
            logger.error("Connection to Storage Service failed. Unable to make request to GoogleMap API without information from Storage Service.");
            return null;
        } 
        Request request = RequestUtil.getGetRequest(getGoogleUri(address));
        ResponseBody responseBody = RequestUtil.getResponse(request).body();
        return getGeocodeResult(responseBody);
    }

    private String getGoogleUri(String address){
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        String apyKey = getApyKey();
        String googleApi = "https://maps.googleapis.com/maps/api/geocode/json?address=";
        return googleApi + encodedAddress + "&key=" + apyKey;
    }

    private GeocodeResult getGeocodeResult(ResponseBody responseBody) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(Objects.requireNonNull(responseBody).string(), GeocodeResult.class);
    }

    @Nullable
    private String getApyKey() {
        Dotenv dotenv;
        dotenv = Dotenv.configure().load();
        return dotenv.get("API_KEY");
    }
}

