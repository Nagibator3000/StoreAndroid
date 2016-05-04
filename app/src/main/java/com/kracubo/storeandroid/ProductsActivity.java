package com.kracubo.storeandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.io.IOException;

public class ProductsActivity extends AppCompatActivity {
    private static final String LOG_TAG = "productsActivity";
    private RecyclerView recyclerView;
    private ProductsAdapter adapter;
    private EditText editName;
    private EditText editPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_activity);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductsAdapter(this);
        recyclerView.setAdapter(adapter);
        findViewById(R.id.addProductBtn).setOnClickListener(v -> addProductAndRefreshTable());
        editName = ((EditText) findViewById(R.id.textName))     ;
        editPrice = ((EditText) findViewById(R.id.textPrice));

        reloadData();
    }

    public void addProductAndRefreshTable() {
        String productName = editName.getText().toString();
        String productPrice = editPrice.getText().toString();
        new Thread(() -> {
            try {
                ApiClient.getInstance().addProduct(productName, productPrice);

               runOnUiThread(()-> {editName.setText("");
                   editPrice.setText("");});

                reloadData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

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
