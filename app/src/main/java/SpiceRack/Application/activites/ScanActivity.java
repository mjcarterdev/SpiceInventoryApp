package SpiceRack.Application.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.Toast;


import java.util.Collections;
import java.util.List;

import SpiceRack.Application.database.Spice;
import SpiceRack.Application.database.SpiceDao;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.Application.database.SpiceListAdapter;
import SpiceRack.Application.utilities.Navigation;
import SpiceRack.databinding.ScanActivityBinding;


public class ScanActivity extends AppCompatActivity implements SpiceListAdapter.SpiceOnClickListener {

    SpiceDatabase mySpiceRackDb;
    SpiceDao mySpiceDao;
    Spice receivedSpice;
    List<Spice> spiceList;
    ScanActivityBinding scanLayout;
    RecyclerView.Adapter adapter;
    Navigation nav;
    GestureDetectorCompat myGesture;

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

        nav = new Navigation(this);
        myGesture = new GestureDetectorCompat(this, new MyGestureListener());

        new ItemTouchHelper(itemTouchCallback).attachToRecyclerView(scanLayout.rvDisplaySpiceList);
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
            String spiceStock = String.valueOf(receivedSpice.getStock());
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
            sum+= element.getStock();
        }
        scanLayout.tvDisplayMessage.setText(receivedSpice.getInfo(sum));
        adapter = new SpiceListAdapter(spiceList, this);
        scanLayout.rvDisplaySpiceList.setAdapter(adapter);
    }

    @Override
    public void spiceOnClick(int position) {
        Toast.makeText(this, "Spice is pressed", Toast.LENGTH_SHORT).show();
    }

    ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Spice spice = spiceList.get(viewHolder.getAdapterPosition());
            int stock = spiceList.get(viewHolder.getAdapterPosition()).getStock();
            if(direction == ItemTouchHelper.RIGHT){
                int newStock = stock - 1;
                spice.setStock(newStock);
                mySpiceDao.upDate(spice);
            }else{
                int newStock = stock + 1;
                spice.setStock(newStock);
                mySpiceDao.upDate(spice);
            }
            updateUI();
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.myGesture.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
            float diffY = moveEvent.getY() - downEvent.getY();
            float diffX = moveEvent.getX() - downEvent.getX();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                //right or left swipe
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        nav.homePage();
                    } else {
                        nav.inventoryPage(); }
                }
            }
            return true;
        }
    }
}