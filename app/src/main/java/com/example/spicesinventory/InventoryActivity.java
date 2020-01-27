package com.example.spicesinventory;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spice_sqlite_test.R;

public class InventoryActivity extends AppCompatActivity {
    myDbController database;
    TextView BarcodeShow, NameShow, StockShow;
    public View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_activity);
        BarcodeShow = findViewById(R.id.BarcodeShow);
        NameShow = findViewById(R.id.NameShow);
        StockShow = findViewById(R.id.StockShow);

        database = new myDbController(this);
        viewSpiceData(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewSpiceData(view);
    }

    public void viewSpiceData(View view) {
        String barcode = database.getBarcode();
        String name = database.getName();
        String stock = database.getStock();
        BarcodeShow.setText(barcode);
        NameShow.setText(name);
        StockShow.setText(stock);
    }
}
