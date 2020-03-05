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
 *  @author Michael and Astrid
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
     * On resuming the activity updateUI() is called to refresh the recycler view data.
     */
    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    /* Check with Karri - the suggested notifyODataChange() method does not work. Currently using
     * method below. Functional but not very efficient.
     */
    public void updateUI() {
        spiceList = mySpiceDao.getAllSpices();
        Collections.sort(spiceList);
        adapter = new SpiceListAdapter(spiceList, this);
        inventoryLayout.rvSpiceList.setAdapter(adapter);
    }

    /**
     * <p>
     *     Analyzes the given motion event and if applicable triggers the appropriate callbacks on
     *     the GestureDetector.OnGestureListener supplied.
     * </p>
     * @param event the current motion event
     * @return true if the GestureDetector.OnGestureListener consumed the event, else false.
     */

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

    /**
     * <p>
     *     MyGestureListener class extends GestureDetector.SimpleOnGestureListener a convenient class
     *     where only a subset of gestures are required. This implements onDown() and onFLing() methods
     *     but does nothing and return false.
     * </p>
     */

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        /**
         * <p>
         *      Swipe_Threshold is the distance travelled from the initial screen contact till the
         *      final position in any of the four directions.
         * </p>
         * <p>
         *      Swipe_Velocity_Threshold is the speed at which a movement needs to travel before being
         *      identified as a gesture.
         * </p>
         * <p> The value given is an arbitrary number based on what feels right</p>
         */
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        /**
         *
         * @param downEvent The first down motion that started the fling.
         * @param moveEvent The move motion event that triggered the current onFLing.
         * @param velocityX The velocity of this fling measured in pixels per second along the x axis.
         * @param velocityY The velocity of this fling measured in pixels per second along the y axis.
         * @return Boolean. If false nothing will happen. If returned true the event has been completed
         * successfully. The outcome of the return is dependent on the direction of this fling.
         */

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
