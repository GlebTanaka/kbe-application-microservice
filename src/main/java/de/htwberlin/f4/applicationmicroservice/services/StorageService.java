package de.htwberlin.f4.applicationmicroservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htwberlin.f4.applicationmicroservice.models.product.Product;
import de.htwberlin.f4.applicationmicroservice.models.storage.StorageObject;
import okhttp3.*;
import okhttp3.Request.Builder;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * Klasse um ein den Rest der Attrubte der Producte zu erfragen.
 */
@Service
public class StorageService {

    private final String storageApi = "http://localhost:8084/api/v1/storage/product/";

    public StorageObject getStorage(UUID uuid) throws IOException {
        Request request = getGetRequest(storageApi + uuid.toString());
        ResponseBody responseBody = getResponse(request).body();
        return getStorageObject(responseBody);
    }

    public void postStorage(Product product) {
        String json = storageWrapper(product);
        RequestBody requestBody = getJsonRequestBody(json);
        Request request = getPostRequest(storageApi, requestBody);
        try {
            Response response = getResponse(request);
        } catch (IOException e) {
            System.err.println("Storage Repository is not running, Connection failed");
        }
    }

    private StorageObject getStorageObject(ResponseBody responseBody) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(Objects.requireNonNull(responseBody).string(), StorageObject.class);
    }

    private Response getResponse(Request request) throws IOException{
        OkHttpClient client = getClient();
        return client.newCall(request).execute();
    }

    private RequestBody getJsonRequestBody(String json){
        return RequestBody.create(json, MediaType.parse("application/json"));
    }

    private Builder getRequestBuilder(String uri){
        return new Request.Builder().url(uri);
    }

    private Request getPostRequest(String uri, RequestBody requestBody){
        Builder builder = getRequestBuilder(uri);
        builder = builder.post(requestBody);
        return buildRequest(builder);
    }

    private Request getGetRequest(String uri){
        Builder builder = getRequestBuilder(uri);
        builder = builder.get();
        return buildRequest(builder);
    }

    private Request buildRequest(Builder builder){
        return builder.build();
    }

    private OkHttpClient getClient(){
        return new OkHttpClient();
    }

    @NotNull
    private String storageWrapper(Product storageObject) {
        return "{\"id\":" + "\"" + storageObject.getId().toString() + "\""
                + ",\"amount\":" + storageObject.getAmount().toString()
                + ",\"place\":" + "\"" + storageObject.getPlace() + "\""
                + ",\"duration\":" + storageObject.getDeliveryTime().toString() + "}";
    }
}
