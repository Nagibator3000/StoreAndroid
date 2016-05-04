package com.kracubo.storeandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProductsActivity extends AppCompatActivity {
    private static final String LOG_TAG = "productsActivity";
    private RecyclerView recyclerView;
    private ProductsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_activity);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductsAdapter();
        recyclerView.setAdapter(adapter);
        new Thread(() -> {
            try {
                testGetProducts();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private  void testGetProducts() throws IOException {
        Log.d(LOG_TAG, "testGetProducts");
        String jsonString = getUrl("http://192.168.56.1:8080/api/products/");
        Product[] products = new Gson().fromJson(jsonString, Product[].class);
        runOnUiThread(()->{adapter.setItems(products);});

    }

    private  String getUrl(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
