package com.example.spicesinventory.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.example.spice_sqlite_test.R;
import com.example.spicesinventory.database.Spice;
import com.example.spicesinventory.database.SpiceDao;
import com.example.spicesinventory.database.Spice_Database;


public class ScanActivity extends AppCompatActivity {

    TextView Barcode, SpiceName, Stock,tvYouHave;
    Spice_Database mySpiceRackDb;
    SpiceDao mySpiceDao;
    Spice receivedSpice;
    String spiceName, spiceStock, spiceMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);
        Barcode = findViewById(R.id.tvBarcode);
        SpiceName = findViewById(R.id.tvSpiceNameScanned);
        Stock = findViewById(R.id.tvStockFound);
        tvYouHave = findViewById(R.id.tvYouHave);

        Intent incomingIntent = getIntent();
        String incomingBarcode = incomingIntent.getStringExtra("ScannedBarcode");
        Barcode.setText(incomingBarcode);

        mySpiceRackDb = Spice_Database.getINSTANCE(this);
        mySpiceDao = mySpiceRackDb.getSpiceDao();
        receivedSpice = mySpiceDao.getSpiceByBarcode(Barcode.getText().toString());

        spiceName = receivedSpice.getSpice_name();
        spiceStock = receivedSpice.getStock();
        spiceMessage = receivedSpice.getInfo();

        SpiceName.setText(spiceName);
        Stock.setText(spiceStock);
        tvYouHave.setText(spiceMessage);

    }



}