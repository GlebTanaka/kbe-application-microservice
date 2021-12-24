package de.htwberlin.f4.applicationmicroservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htwberlin.f4.applicationmicroservice.services.storage.StorageObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

/**
 * Klasse um ein den Rest der Attrubte der Producte zu erfragen.
 */
@Service
public class StorageService {

    /**
     * Methode um bei der Storage API die restliche Information zu bekommen.
     * @param uuid UUID das erfragte Produkt
     * @return StorageObject Obejkt mit der Restinformation zum Product
     * @throws IOException wenn Response null ist
     */
    public StorageObject getStorage(UUID uuid) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:8084/api/v1/storage/product/" + uuid.toString())
                .get()
                .build();
        ResponseBody responseBody = client.newCall(request).execute().body();
        ObjectMapper objectMapper = new ObjectMapper();
        StorageObject storageObject = objectMapper.readValue(responseBody.string(), StorageObject.class);
        return storageObject;
    }
}