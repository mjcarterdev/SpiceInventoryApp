package com.example.spicesinventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.spice_sqlite_test.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class HomeActivity extends AppCompatActivity{

    EditText Barcode, SpiceName, Stock, updateStock;
    TextView BarcodeShow, NameShow, StockShow;
    myDbController database;
    public View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        Barcode= findViewById(R.id.editBarcode);
        SpiceName= findViewById(R.id.editSpiceName);
        Stock= findViewById(R.id.editStock);
        BarcodeShow = findViewById(R.id.BarcodeShow);
        NameShow = findViewById(R.id.NameShow);
        StockShow = findViewById(R.id.StockShow);
        updateStock = findViewById(R.id.updateStock);

        database = new myDbController(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void addSpice(View view) {
        String barcodeNumber = Barcode.getText().toString();
        String spiceName = SpiceName.getText().toString();
        int stock = Integer.parseInt(Stock.getText().toString());

        if(barcodeNumber.isEmpty() || spiceName.isEmpty() || stock == 0)
        {
            Toast.makeText(this,"fill in all boxes",Toast.LENGTH_LONG).show();
        }else{
            long id = database.insertData(barcodeNumber, spiceName, stock);
            if(id <= 0){
                Toast.makeText(this, "Insertion unsuccessful", Toast.LENGTH_LONG).show();
                Barcode.setText("");
                SpiceName.setText("");
                Stock.setText("");
            }else{
                Toast.makeText(this, "Insertion successful", Toast.LENGTH_LONG).show();
                Barcode.setText("");
                SpiceName.setText("");
                Stock.setText("");

            }
        }
    }

    public void deleteSpice(View view) {
        String toDelete = Barcode.getText().toString();
        if(toDelete.isEmpty())
        {
            Toast.makeText(this, "Enter Spice Name", Toast.LENGTH_LONG).show();
        }
        else{
            int a= database.delete(toDelete);
            if(a<=0)
            {

                Toast.makeText(this, toDelete+" Deletion was unsuccessful", Toast.LENGTH_LONG).show();
                SpiceName.setText("");
            }
            else
            {
                Toast.makeText(this, toDelete+" was Deleted", Toast.LENGTH_LONG).show();
                SpiceName.setText("");

            }
        }
    }

    public void viewSpiceData(View view) {
        String barcode = database.getBarcode();
        String name = database.getName();
        String stock = database.getStock();
        BarcodeShow.setText(barcode);
        NameShow.setText(name);
        StockShow.setText(stock);
    }

    public void updateSpice(View view) {
        String toUpdate = Barcode.getText().toString();
        int newStock = Integer.parseInt(updateStock.getText().toString());
        if(toUpdate.isEmpty())
        {
            Toast.makeText(this, "Enter Barcode", Toast.LENGTH_LONG).show();
        }
        else{
            int a= database.updateStock(toUpdate, newStock);
            if(a<=0)
            {
                Toast.makeText(this, toUpdate+" update was unsuccessful", Toast.LENGTH_LONG).show();
                SpiceName.setText("");
            }
            else
            {
                Toast.makeText(this, toUpdate+" was Updated", Toast.LENGTH_LONG).show();
                SpiceName.setText("");
                
            }
        }
    }

    public void scanner(View v) {
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Barcode.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void ShoppingPage(View view) {
        Intent openActivity = new Intent(this, ShoppingListActivity.class);
        startActivity(openActivity);
    }

    public void InventoryPage(View view) {
        Intent openActivity = new Intent(this, InventoryActivity.class);
        startActivity(openActivity);
    }
}