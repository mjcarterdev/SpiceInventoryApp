package com.example.spicesinventory.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spice_sqlite_test.R;
import com.example.spicesinventory.database.Spice;
import com.example.spicesinventory.database.SpiceDao;
import com.example.spicesinventory.database.SpiceListAdapter;
import com.example.spicesinventory.database.Spice_Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.util.List;

public class InventoryActivity extends AppCompatActivity {

    EditText editBarcode, editName, editStock;
    Button deleteSpice, insertSpice;
    RecyclerView listSpice;
    RecyclerView.Adapter adapter;
    String barcode,barcodeID, name, stock, scannedBarcode;
    Spice_Database mySpiceRackDb;
    SpiceDao mySpiceDao;
    Spice temp;
    FloatingActionButton fabScanner;

    private View.OnClickListener myClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.deleteSpices:
                    deleteSpice();
                    break;
                case R.id.btnaddSpiceInventory:
                    addSpice();
                    break;
                default:
                    scanner(v);
                    barcode = editBarcode.getText().toString();
            }
            updateUI();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_activity);
        editBarcode = findViewById(R.id.editBarcode);
        editName = findViewById(R.id.editSpiceName);
        editStock = findViewById(R.id.tvStock);
        deleteSpice = findViewById(R.id.deleteSpices);
        insertSpice = findViewById(R.id.btnaddSpiceInventory);
        listSpice = findViewById(R.id.rvSpiceList);
        fabScanner = findViewById(R.id.fabScanBarcode);

        mySpiceRackDb = Spice_Database.getINSTANCE(this);
        mySpiceDao = mySpiceRackDb.getSpiceDao();

        deleteSpice.setOnClickListener(myClick);
        insertSpice.setOnClickListener(myClick);
        fabScanner.setOnClickListener(myClick);

        listSpice.setLayoutManager(new LinearLayoutManager(this));
        updateUI();
    }


    public void insert(String barcode, String name, String stock) {
        Spice spice = new Spice(barcode, name, stock);
        mySpiceDao.insertSpice(spice);
    }

    public void delete(String barcode) {
        Spice toDelete = mySpiceDao.getSpiceByBarcode(barcode);
        mySpiceDao.deleteSpice(toDelete);
    }

    public void updateUI() {
        List<Spice> spices = mySpiceDao.getAllSpices();
        adapter = new SpiceListAdapter(spices);
        listSpice.setAdapter(adapter);
    }

    /*
    Barcode Scanner
    */
    public void scanner(View v) {
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                scannedBarcode = result.getContents();
                editBarcode.setText(scannedBarcode);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void addSpice() {
        barcode = editBarcode.getText().toString();
        name = editName.getText().toString();
        stock = editStock.getText().toString();
        temp = mySpiceDao.getSpiceByBarcode(barcode);
        if(temp != null)
            barcodeID = temp.getBarcodeID();


        if (barcode.isEmpty() || name.isEmpty() || stock.isEmpty()) {
            Toast.makeText(this, "Error - one of the boxes is empty. Please fill all details.", Toast.LENGTH_LONG).show();

        } else if (barcode.equals(barcodeID)) {
            Toast.makeText(this, barcode + " Already exists " + barcodeID, Toast.LENGTH_LONG).show();
        }
        else if (temp == null) {
            insert(barcode, name, stock);
            setDefaultInfo();
        }
    }

    public void deleteSpice(){
        barcode = editBarcode.getText().toString();
        temp = mySpiceDao.getSpiceByBarcode(barcode);
        if (temp == null) {
            Toast.makeText(this, "Error - Barcode was not found! Please try again.", Toast.LENGTH_LONG).show();
        } else {
            delete(barcode);
            setDefaultInfo();
        }
    }

    public void setDefaultInfo() {
        editBarcode.setText("");
        editName.setText("");
        editStock.setText("");

    }
}
