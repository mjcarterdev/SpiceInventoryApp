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

/**
 * <p>
 *      Scan Activity class displays Spice Information based on information gathered
 *      from barcode scanning or user clicks from the Spice Inventory activity. The activity shows
 *      all the key information based on the selected spice. It displays, barcode number, spice name
 *      and stock. While also displaying all the current spices with the same name that are in the
 *      inventory database. The spice list can have its stock updated with swipe gesture.
 * </p>
 *
 */

public class ScanActivity extends AppCompatActivity implements SpiceListAdapter.SpiceOnClickListener {

    SpiceDatabase mySpiceRackDb;
    SpiceDao mySpiceDao;
    Spice receivedSpice;
    List<Spice> spiceList;
    ScanActivityBinding scanLayout;
    RecyclerView.Adapter adapter;
    Navigation nav;
    GestureDetectorCompat myGesture;

    /**
     * <p>
     *      The barcode from passed intent is set to the activity. Database is
     *      instantiated and sorted. Additional Spice information is gather from method
     *      seachByBarcode() using barcode from intent.
     * </p>
     */
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
        if(receivedSpice == null) {
            Toast.makeText(this, "Error Barcode not in Inventory", Toast.LENGTH_SHORT).show();
        }else{
            updateUI();
        }
    }

    /**
     * <p>
     *     searchByBarcode() takes the barcode string sent from the intent and retrieves the object
     *     from the database. If the search parameter is null then default messages are set to the
     *     activity. If not null then the objects data is retrieved and set to the activity for
     *     display.
     * </p>
     */
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

    /**
     * <p>
     *      Check with Karri - the suggested notifyODataChange() method does not work. Currently
     *      using method below. Functional but not very efficient.
     * </p>
     * <p>
     *      Creates a list of Spice objects defined by Spice Name found from method
     *      searchByBarcode(). List is sorted alphabetically and a total stock is calculated
     *      using getStock() method on each list object. The sum is passed to receivedSpice.getInfo()
     *      and is displayed within the activity.
     * </p>
     */
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
            searchByBarcode();
        }


    @Override
    public void spiceOnClick(int position) {
    }

    /**
     * <p>
     *     A new ItemTouchHelper.SimpleCallback that @overrides onMove() and onSwipe() methods.
     *     The onSwipe is used to add or subtract one from Spice object's stock variable.
     *     The object is selected from the recyclerView by pressing and swiping either left or
     *     right. A right swipe will subtract and a left swipe will add. Both actions will then
     *     update the object within the Room database table.
     * </p>
     *
     */
    ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        /**
         *
         * @param viewHolder the attached RecyclerView from the layout that has the ItemTouchHelper
         *                   assigned.
         * @param direction predefined Android gesture recognition of swipe direction.
         */
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

    /**
     * <p>
     *     When an item from the recycler is pressed, spiceOnClick() is called. This creates an
     *     intent that stores an extra String of the barcode information for the object at the
     *     called position. The intent is to start the scanActivity class and pass the barcode with
     *     it.
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
                        nav.homePage();
                    } else {
                        nav.inventoryPage(); }
                }
            }
            return true;
        }
    }
}