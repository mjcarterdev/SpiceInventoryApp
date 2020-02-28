package SpiceRack.Application.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import SpiceRack.Application.database.Spice;
import SpiceRack.Application.database.SpiceDao;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.Application.database.SpiceListAdapter;
import SpiceRack.Application.utilities.RecyclerViewClickInterface;
import SpiceRack.databinding.ScanActivityBinding;


public class ScanActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    SpiceDatabase mySpiceRackDb;
    SpiceDao mySpiceDao;
    Spice receivedSpice;
    List<Spice> spiceList;
    ScanActivityBinding scanLayout;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scanLayout = ScanActivityBinding.inflate(LayoutInflater.from(this));
        setContentView(scanLayout.getRoot());

        Intent incomingIntent = getIntent();
        String incomingBarcode = incomingIntent.getStringExtra("ScannedBarcode");
        scanLayout.tvDisplayBarcode.setText(incomingBarcode);
        mySpiceRackDb = SpiceDatabase.getINSTANCE(this);
        mySpiceDao = mySpiceRackDb.getSpiceDao();
        spiceList = mySpiceDao.getAllSpices();
        Collections.sort(spiceList);
        searchByBarcode();

        scanLayout.rvDisplaySpiceList.setLayoutManager(new LinearLayoutManager(this));
        updateUI();

    }

    public void searchByBarcode(){
        mySpiceDao = mySpiceRackDb.getSpiceDao();
        receivedSpice = mySpiceDao.getSpiceByBarcode(scanLayout.tvDisplayBarcode.getText().toString());

        if(receivedSpice == null){
            String barcodeNotFound = "Barcode not found!";
            scanLayout.tvDisplayMessage.setText(barcodeNotFound);
            scanLayout.tvDisplaySpiceName.setText("");
            scanLayout.tvDisplayStock.setText("");
        }else {
            String spiceName = receivedSpice.getSpiceName();
            String spiceStock = receivedSpice.getStock();
            String spiceID = String.valueOf(receivedSpice.getSpiceID());

            scanLayout.tvDisplaySpiceName.setText(spiceName);
            scanLayout.tvDisplayStock.setText(spiceStock);
            scanLayout.tvDisplaySpiceID.setText(spiceID);
        }
    }

    public void updateUI() {
        spiceList = mySpiceDao.getSpiceByName(scanLayout.tvDisplaySpiceName.getText().toString());
        Collections.sort(spiceList);
        int sum = 0;
        for (Spice element: spiceList) {
            sum+= Integer.parseInt(element.getStock());
        }
        scanLayout.tvDisplayMessage.setText(receivedSpice.getInfo(sum));
        adapter = new SpiceListAdapter(spiceList, this);
        scanLayout.rvDisplaySpiceList.setAdapter(adapter);
    }


    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onLongItemClick(int position) {

    }


}