package com.example.spicesinventory.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.example.spice_sqlite_test.R;


public class ScanActivity extends AppCompatActivity {

    TextView Barcode, SpiceName, Stock,tvYouHave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);
        Barcode = findViewById(R.id.tvBarcode);
        SpiceName = findViewById(R.id.editSpiceName);
        Stock = findViewById(R.id.tvStock);
        tvYouHave = findViewById(R.id.tvYouHave);

        Intent incomingIntent = getIntent();
        String incomingBarcode = incomingIntent.getStringExtra("ScannedBarcode");
        Barcode.setText(incomingBarcode);

    }



}