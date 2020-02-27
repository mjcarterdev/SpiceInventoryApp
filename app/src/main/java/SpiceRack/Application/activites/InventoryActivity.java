package SpiceRack.Application.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import SpiceRack.Application.database.Spice;
import SpiceRack.Application.database.SpiceDao;
import SpiceRack.Application.database.SpiceListAdapter;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.Application.utilities.Navigation;

import SpiceRack.Application.utilities.RecyclerViewClickInterface;
import SpiceRack.databinding.InventoryActivityBinding;

import java.util.Collections;
import java.util.List;

public class InventoryActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    InventoryActivityBinding inventoryLayout;
    RecyclerView.Adapter adapter;
    SpiceDatabase mySpiceRackDb;
    SpiceDao mySpiceDao;
    Navigation nav;
    List<Spice> spiceList;
    private GestureDetectorCompat myGesture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inventoryLayout = InventoryActivityBinding.inflate(LayoutInflater.from(this));
        setContentView(inventoryLayout.getRoot());

        nav = new Navigation(this);
        myGesture = new GestureDetectorCompat(this, new MyGestureListener());

        mySpiceRackDb = SpiceDatabase.getINSTANCE(this);
        mySpiceDao = mySpiceRackDb.getSpiceDao();

        inventoryLayout.rvSpiceList.setLayoutManager(new LinearLayoutManager(this));
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        spiceList = mySpiceDao.getAllSpices();
        Collections.sort(spiceList);
        adapter = new SpiceListAdapter(spiceList, this);
        inventoryLayout.rvSpiceList.setAdapter(adapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.myGesture.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onItemClick(int position) {
        String scannedBarcode = spiceList.get(position).getBarcode();
        Intent intent = new Intent(InventoryActivity.this, ScanActivity.class);
        intent.putExtra("ScannedBarcode", scannedBarcode);
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {
        String scannedBarcode = spiceList.get(position).getBarcode();
        Intent intent = new Intent(InventoryActivity.this, ScanActivity.class);
        intent.putExtra("ScannedBarcode", scannedBarcode);
        startActivity(intent);
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
            boolean result = false;
            float diffY = moveEvent.getY() - downEvent.getY();
            float diffX = moveEvent.getX() - downEvent.getX();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                //right or left swipe
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        nav.inventoryEditor();
                    } else {
                        nav.homePage(); }
                }
            }
            return result;
        }
    }

}
