package de.htwberlin.f4.applicationmicroservice.util;

import java.io.IOException;

import okhttp3.*;
import okhttp3.Request.Builder;

public final class RequestUtil {
    private static Builder getRequestBuilder(String uri) {
        return new Request.Builder().url(uri);
    }

    public static Request getPostRequest(String uri, RequestBody requestBody) {
        Builder builder = getRequestBuilder(uri);
        builder = builder.post(requestBody);
        return buildRequest(builder);
    }

    public static Request getGetRequest(String uri) {
        Builder builder = getRequestBuilder(uri);
        builder = builder.get();
        return buildRequest(builder);
    }

    private static Request buildRequest(Builder builder) {
        return builder.build();
    }

    private static OkHttpClient getClient() {
        return new OkHttpClient();
    }

    public static Response getResponse(Request request) throws IOException {
        OkHttpClient client = getClient();
        return client.newCall(request).execute();
    }
}
