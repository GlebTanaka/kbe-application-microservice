package de.htwberlin.f4.applicationmicroservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htwberlin.f4.applicationmicroservice.models.googlemaps.GeocodeResult;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Zugriff auf Addressinformation
 */
@Service
public class GoogleMapsService {

    /**
     * Liefert ein GeocodeResult Object im JSON-Format
     *
     * @param address String, Ort des Productes
     * @return GeocodeResult Ergebnis der Anfrage
     * @throws IOException wenn es keine Resoponse gibt
     */
    public GeocodeResult getGeocode(String address) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        String apyKey = getApyKey();
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/geocode/json?address="
                        + encodedAddress
                        + "&key=" + apyKey)
                .get()
                .build();
        ResponseBody responseBody = client.newCall(request).execute().body();
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

