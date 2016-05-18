package com.kracubo.storeandroid;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiClient {
    private static final String LOG_TAG = "apiClient";
   // private static final String END_POINT = "http://192.168.1.37:8080/";
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

    public void deleteProduct(long id) {
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("product_id", id + "")
                    .build();
            Request request = new Request.Builder()
                    .url(END_POINT + "api/products/delete")
                    .post(formBody)
                    .build();

            Response response = null;

            response = client.newCall(request).execute();

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addProduct(String name, String price) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("product_name", name)
                .add("product_price", price)
                .build();
        Request request = new Request.Builder()
                .url(END_POINT + "api/products/add")
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
    }

    public Customer[] getCustomers() throws IOException {
        String jsonString = getUrl(END_POINT + "api/customers/");
        Customer[] customers = new Gson().fromJson(jsonString, Customer[].class);
        return new Gson().fromJson(jsonString, Customer[].class);
    }

    public void deleteCustomer(long id) {
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("customer_id", id + "")
                    .build();
            Request request = new Request.Builder()
                    .url(END_POINT + "api/customer/delete")
                    .post(formBody)
                    .build();

            Response response = null;

            response = client.newCall(request).execute();

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void addCustomer(String name, String date) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("customer_name", name)
                .add("customer_date", date)
                .build();
        Request request = new Request.Builder()
                .url(END_POINT + "api/customers/add")
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
    }



}

