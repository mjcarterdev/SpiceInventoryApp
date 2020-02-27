package SpiceRack.Application.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import SpiceRack.Application.database.Spice;
import SpiceRack.Application.database.SpiceDao;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.R;


public class ScanActivity extends AppCompatActivity {

    TextView Barcode, SpiceName, Stock, tvYouHave, tvTest;
    SpiceDatabase mySpiceRackDb;
    SpiceDao mySpiceDao;
    Spice receivedSpice;
    List<Spice> spiceList;
    String spiceName, spiceStock, spiceMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);
        Barcode = findViewById(R.id.tvBarcode);
        SpiceName = findViewById(R.id.tvSpiceNameScanned);
        Stock = findViewById(R.id.tvStockFound);
        tvYouHave = findViewById(R.id.tvYouHave);
        tvTest = findViewById(R.id.tvTest);


        Intent incomingIntent = getIntent();
        String incomingBarcode = incomingIntent.getStringExtra("ScannedBarcode");
        Barcode.setText(incomingBarcode);
        mySpiceRackDb = SpiceDatabase.getINSTANCE(this);
        mySpiceDao = mySpiceRackDb.getSpiceDao();
        spiceList = mySpiceDao.getAllSpices();
        Collections.sort(spiceList);
        searchByBarcode();

    }

    public void searchByBarcode(){
        mySpiceDao = mySpiceRackDb.getSpiceDao();
        receivedSpice = mySpiceDao.getSpiceByBarcode(Barcode.getText().toString());

        if(receivedSpice == null){
            String barcodeNotFound = "Barcode not found!";
            tvYouHave.setText(barcodeNotFound);
            SpiceName.setText("");
            Stock.setText("");
        }else {
            spiceName = receivedSpice.getSpiceName();
            spiceStock = receivedSpice.getStock();
            spiceMessage = receivedSpice.getInfo();
            String spiceID = String.valueOf(receivedSpice.getSpiceID());

            SpiceName.setText(spiceName);
            Stock.setText(spiceStock);
            tvYouHave.setText(spiceMessage);
            tvTest.setText(spiceID);
        }
    }



}