package com.kracubo.storeandroid;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiClient {
    private static final String LOG_TAG = "apiClient";
    private static final String END_POINT = "http://192.168.56.1:8080/";
    static ApiClient instance = new ApiClient();
    private OkHttpClient client;

    private ApiClient() {
        //TODO init stuff
        client = new OkHttpClient();
    }

    public static ApiClient getInstance() {
        return instance;
    }

    public Product[] getProducts() throws IOException {
        Log.d(LOG_TAG, "getProducts");
        String jsonString = getUrl(END_POINT + "api/products/");
        return new Gson().fromJson(jsonString, Product[].class);
    }

    private String getUrl(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
