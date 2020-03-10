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
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import java.util.Collections;
import java.util.List;

import SpiceRack.Application.database.Spice;
import SpiceRack.Application.database.SpiceDao;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.Application.database.SpiceListAdapter;
import SpiceRack.Application.utilities.Navigation;
import SpiceRack.R;
import SpiceRack.databinding.ScanActivityBinding;

import static android.view.View.VISIBLE;

/**
 * <p>
 *      Scan Activity class displays Spice Information based on information gathered
 *      from barcode scanning or user clicks from the Spice Inventory activity. The activity shows
 *      all the key information based on the selected spice. It displays, barcode number, spice name
 *      and stock. While also displaying all the current spices with the same name that are in the
 *      inventory database. The spice list can have its stock updated with swipe gesture.
 * </p>
 *
 *  @author Michael
 *  @author Astrid
 *  @version 1.0
 *  @since 05.03.2020
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
     *      instantiated and sorted alphabetically. Additional Spice information is gather from method
     *      searchByBarcode() using barcode from intent. A itemTouchCallback is set to the recyclerview
     *      listening for swipes left or right.
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
        ImageButton information = scanLayout.ibInformation;
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isVisible();
            }
        });
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
     *      Creates a list of Spice objects defined by Spice Name found from method
     *      searchByBarcode(). List is sorted alphabetically and a total stock is calculated
     *      using getStock() method on each list object. The sum is passed to receivedSpice.getInfo()
     *      and is displayed within the refreshed activity.
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
         * <p>
         *     onSwiped will differentiate between a left or right swipe. If the swipe is right
         *     the items stock will decrease by 1. If the swipe is left the item stock will increase
         *     by 1. then UpdateUI is called to refresh the recyclerview.
         * </p>
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

    public void isVisible(){
        if(scanLayout.tvHomeInstructionScan.getVisibility() == VISIBLE){
            scanLayout.tvHomeInstructionScan.setVisibility(View.INVISIBLE);

        }else{
            scanLayout.tvHomeInstructionScan.setVisibility(VISIBLE);

        }
    }
}