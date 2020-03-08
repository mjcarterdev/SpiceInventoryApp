package SpiceRack.Application.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import SpiceRack.Application.database.Spice;
import SpiceRack.Application.database.SpiceDao;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.Application.database.SpiceListAdapter;
import SpiceRack.Application.utilities.Navigation;
import SpiceRack.databinding.InventoryActivityBinding;

/**
 *  <p>
 *      InventoryActivity class creates and displays the Spice Inventory. It implements the custom
 *      SpiceListAdapter with a onClickListener so when the item is pressed it passes the Spice object
 *      to ScanActivity. The class has also has gesture control navigation.</p>
 *
 *  @author Michael
 *  @author Astrid
 *  @version 1.0
 *  @since 05.03.2020
 */

public class InventoryActivity extends AppCompatActivity implements SpiceListAdapter.SpiceOnClickListener{

    InventoryActivityBinding inventoryLayout;
    RecyclerView.Adapter adapter;
    SpiceDatabase mySpiceRackDb;
    SpiceDao mySpiceDao;
    List<Spice> spiceList;
    Navigation nav;
    private GestureDetectorCompat myGesture;

    /**
     *  <p>onCreate() calls and instantiates a singleton of the spice inventory database using Room.</p>
     *
     * @param savedInstanceState saved instance state of the activity.
     */

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

    /**
     * <p> On resuming the activity updateUI() is called to refresh the recycler view data. </p>
     */

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    /**
     * <p>
     *      updateUI() is called to refresh the recycler view after data change. After changes occur the
     *      list is called again and sorted alphabetically and the recycler is reset.
     * </p>
     */

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

    /**
     * <p>
     *     When an item from the recycler is pressed, spiceOnClick() is called. This creates an
     *     intent that stores an extra String of the barcode information for the object at the
     *     called position. The intent is to start the scanActivity class and pass the barcode with
     *     it.
     * </p>
     * @param position is the location within the list of the recyclerview when an click event is
     *                 registered.
     */

    @Override
    public void spiceOnClick(int position) {
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
            return true;
        }
    }

}
