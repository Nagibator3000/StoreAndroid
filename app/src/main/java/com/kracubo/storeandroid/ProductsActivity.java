package com.kracubo.storeandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;

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
        adapter = new ProductsAdapter(this);
        recyclerView.setAdapter(adapter);
        reloadData();
    }

    private void reloadData() {
        new Thread(() -> {
            try {
                Product[] products = ApiClient.getInstance().getProducts();
                runOnUiThread(() -> adapter.setItems(products));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void onLongClickProduct(long id) {
        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", (dialog, which) -> deleteProductAndRefreshTable(id))
                .setNegativeButton("No", (dialog1, which1) -> {
                })
                .create()
                .show();

    }

    private void deleteProductAndRefreshTable(long id) {
        new Thread(() -> {
            ApiClient.getInstance().deleteProduct(id);
            reloadData();
        }).start();


    }
}
