package com.kracubo.storeandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.io.IOException;

public class CustomersActivity extends AppCompatActivity {
    private static final String LOG_TAG = "customersActivity";
    private RecyclerView recyclerView;
    private CustomersAdapter adapter;
    private EditText editName;
    private EditText editDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customers_activity);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomersAdapter(this);
        recyclerView.setAdapter(adapter);
        findViewById(R.id.addCustomerBtn).setOnClickListener(v -> addCustomerAndRefreshTable());
        editName = ((EditText) findViewById(R.id.textNameCust))     ;
        editDate = ((EditText) findViewById(R.id.textDate));

        reloadData();
    }

    public void addCustomerAndRefreshTable() {
        String customerName = editName.getText().toString();
        String customerDate = editDate.getText().toString();
        new Thread(() -> {
            try {
                ApiClient.getInstance().addCustomer(customerName, customerDate);

                runOnUiThread(()-> {editName.setText("");
                    editDate.setText("");});

                reloadData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void reloadData() {
        new Thread(() -> {
            try {
                Customer[] customers = ApiClient.getInstance().getCustomers();
                runOnUiThread(() -> adapter.setItems(customers));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void deleteCustomerAndRefreshTable(long id) {
        new Thread(() -> {
            ApiClient.getInstance().deleteCustomer(id);
            reloadData();
        }).start();


    }

    public void onLongClickCustomer(long id) {
        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", (dialog, which) -> deleteCustomerAndRefreshTable(id))
                .setNegativeButton("No", (dialog1, which1) -> {
                })
                .create()
                .show();


    }
}

