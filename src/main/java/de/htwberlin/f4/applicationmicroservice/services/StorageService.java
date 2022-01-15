package de.htwberlin.f4.applicationmicroservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htwberlin.f4.applicationmicroservice.services.storage.StorageObject;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.FormBody.Builder;

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

    public void postStorage(StorageObject storageObject) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("id", storageObject.getId())
            .add("amount", storageObject.getAmount().toString())
            .add("place", storageObject.getPlace())
            .add("duration", storageObject.getDuration().toString()).build();
        Request request = new Request.Builder()
                .url("http://localhost:8084/api/v1/storage/product/")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
    }
}
