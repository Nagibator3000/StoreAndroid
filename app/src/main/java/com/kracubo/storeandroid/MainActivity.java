package com.kracubo.storeandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonProducts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProducts();
            }
        });
        findViewById(R.id.buttonCustomers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomers();
            }
        });
        findViewById(R.id.buttonPurchases).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPurchases();
            }
        });
    }

    private void showPurchases() {
        startActivity(new Intent(this,PurchasesActivity.class));
    }

    private void showCustomers() {

    }


    private void showProducts() {
        startActivity(new Intent(this, ProductsActivity.class));
    }
}
