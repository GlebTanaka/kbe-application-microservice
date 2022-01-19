package de.htwberlin.f4.applicationmicroservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htwberlin.f4.applicationmicroservice.models.product.Product;
import de.htwberlin.f4.applicationmicroservice.models.storage.StorageObject;
import okhttp3.*;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Klasse um ein den Rest der Attrubte der Producte zu erfragen.
 */
@Service
public class StorageService {

    // TODO Bekommt eine JSON file mit allen Storage Objekte, nur notwendig, wenn wir sie in listALl() nutezten wollen
    public List<StorageObject> getAllStorage() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:8084/api/v1/storage")
                .get()
                .build();
        ResponseBody responseBody = client.newCall(request).execute().body();
        System.out.println(responseBody.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        List<StorageObject> storageObjectList = objectMapper.readValue(responseBody.string(), new TypeReference<>() {
        });
        for (StorageObject storageObject : storageObjectList) {
            System.out.println(storageObject.toString());
        }
        return storageObjectList;
    }

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

    public void postStorage(Product storageObject) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String json = "{\"id\":" + "\"" + storageObject.getId().toString() + "\""
                + ",\"amount\":" + storageObject.getAmount().toString()
                + ",\"place\":" + "\"" + storageObject.getPlace() + "\""
                + ",\"duration\":" + storageObject.getDeliveryTime().toString() + "}";

        System.out.println(json);

        RequestBody requestBody = RequestBody.create(
                json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url("http://localhost:8084/api/v1/storage/product/")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
    }
}
