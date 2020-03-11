package SpiceRack.Application.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import SpiceRack.Application.database.Spice;
import SpiceRack.Application.database.SpiceDao;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.Application.utilities.Navigation;
import SpiceRack.R;
import SpiceRack.databinding.InventoryEditorActivityBinding;
import static android.view.View.VISIBLE;

/**
 *<p>
 *      InventoryEditorActivity class is the page where all new Spices are entered into the inventory.
 *      Spice entry is done in two ways, either by pressing the floating button that will create a
 *      barcode scanning instance and save the result in the barcode field, with the other fields
 *      being typed manually. The second way is slightly different in that you manually add the
 *      barcode number instead of using the barcode scanner. Other functions of this page include
 *      deleting spices from the inventory by scanning or typing the barcode and pressing the delete
 *      button, removing said spice from the database. In addition you can fill in the details and
 *      press update that will search the barcode number and update the entry with the new fields
 *      typed in.
 *</p>
 *
 * @author Michael
 * @author Astrid
 * @version 1.0
 * @since 05.03.2020
 */

public class InventoryEditorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String barcode,barcodeID, name, container, brand, stock;
    private SpiceDao mySpiceDao;
    private Spice receivedSpice;
    private InventoryEditorActivityBinding inventoryEditorLayout;
    private Navigation nav;
    private GestureDetectorCompat myGesture;
    private static final String[] containerType = {"Select One...","Jar", "Refill"};
    private static final String[] brands = {"Select One...", "KMarket", "SMarket", "Santa Maria", "Other"};

    private View.OnClickListener myClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.deleteSpices:
                    deleteSpice();
                    break;
                case R.id.btnaddSpice:
                    addSpice();
                    break;
                case R.id.btnUpdateSpice:
                    updateSpice();
                    break;
                case R.id.ibInformation:
                    isVisible();
                    break;
                default:
                    barcode = inventoryEditorLayout.editBarcode.getText().toString();
            }
        }
    };

    /**
     *<p>
     *     onCreate() instantiates the Spice Inventory database and sets the views for the
     *     onClickListeners it also sets the spinners for the dropdown menus. Navigation and gesture
     *     detection are created for additional functionality.
     *</p>
     * @param savedInstanceState saved instance state of the activity.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inventoryEditorLayout = InventoryEditorActivityBinding.inflate(LayoutInflater.from(this));
        setContentView(inventoryEditorLayout.getRoot());

        SpiceDatabase mySpiceRackDb = SpiceDatabase.getINSTANCE(this);
        mySpiceDao = mySpiceRackDb.getSpiceDao();

        inventoryEditorLayout.deleteSpices.setOnClickListener(myClick);
        inventoryEditorLayout.btnaddSpice.setOnClickListener(myClick);
        inventoryEditorLayout.btnUpdateSpice.setOnClickListener(myClick);
        inventoryEditorLayout.ibInformation.setOnClickListener(myClick);

        ArrayAdapter<String> containerAdaptor = new ArrayAdapter<>(InventoryEditorActivity.this, android.R.layout.simple_spinner_dropdown_item, containerType);
        ArrayAdapter<String> brandAdaptor = new ArrayAdapter<>(InventoryEditorActivity.this, android.R.layout.simple_spinner_dropdown_item, brands);

        containerAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        inventoryEditorLayout.spContainerType.setAdapter(containerAdaptor);
        inventoryEditorLayout.spBrand.setAdapter(brandAdaptor);

        inventoryEditorLayout.spContainerType.setOnItemSelectedListener(this);
        inventoryEditorLayout.spBrand.setOnItemSelectedListener(this);

        nav = new Navigation(this);
        myGesture = new GestureDetectorCompat(this, new MyGestureListener());

        inventoryEditorLayout.fBtnScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanner(v);
            }
        });
    }

   //Barcode Scanner
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
                inventoryEditorLayout.editBarcode.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.myGesture.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     *<p>
     *     Callback method to be invoked when an item in this view has been selected.
     *     The id of the parent is checked to ensure the item data is stored in the correct variable.
     *</p>
     * @param parent The AdapterView where the selection happened
     * @param view The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.spContainerType:
                container = inventoryEditorLayout.spContainerType.getSelectedItem().toString();
                break;
            case R.id.spBrand:
                brand = inventoryEditorLayout.spBrand.getSelectedItem().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX < 0) {
                        nav.inventoryPage();
                    }
                }
            }
            return false;
        }
    }

    /**
     *<p>
     *     updateSpice() is called to change the data of an already existing spice within the database.
     *     First the barcode of the spice to be modified should be entered, along with the new information
     *     to update with. This method then checks to makes sure the spice exists, if not will notify
     *     the user. Then it checks for empty fields that are needs to be filled in. Finally, the spice is
     *     updated with the new information and the user is notified of the change.
     *</p>
     */
    private void updateSpice() {
        barcode = inventoryEditorLayout.editBarcode.getText().toString();
        name = inventoryEditorLayout.editSpiceName.getText().toString();
        stock = inventoryEditorLayout.editStock.getText().toString();
        container = inventoryEditorLayout.spContainerType.getSelectedItem().toString();
        brand = inventoryEditorLayout.spBrand.getSelectedItem().toString();
        receivedSpice = mySpiceDao.getSpiceByBarcode(barcode);
        if (receivedSpice == null) {
            Toast.makeText(this, "Error - Barcode was not found! Please try again.", Toast.LENGTH_LONG).show();

        }else if (barcode.isEmpty() || name.isEmpty() || stock.isEmpty() || container.isEmpty() || brand.isEmpty() || container.contains("Select One...") || brand.contains("Select One...")) {
                Toast.makeText(this, "Error - one of the boxes is empty. Please fill all details.", Toast.LENGTH_LONG).show();
        } else {
            String oldName = receivedSpice.getSpiceName();
            receivedSpice.setSpiceName(name);
            receivedSpice.setBrand(brand);
            receivedSpice.setContainerType(container);
            receivedSpice.setStock(Integer.parseInt(stock));
            mySpiceDao.upDate(receivedSpice);
            Toast.makeText(this,  oldName + " was updated to "+ receivedSpice.getSpiceName(), Toast.LENGTH_SHORT).show();
            setDefaultInfo();
        }
    }

    /**
     *<p>
     *     addSpice() is called to add a new spice to the database. It takes the information typed into
     *     the edit fields of the page and creates a new spice object. Validation is carried out
     *     before addition of a new spice. The first check is a search of the database for matching
     *     barcode. If this is not null the barcode is stored for a later check. THe second check is
     *     to make sure all fields have been filled in. the third check uses the the found barcode previously
     *     a compares it to the typed field. If they match then a notification is sent to say it already
     *     exists. Finally if all validation is passed a new spice object is created and added to the
     *     database table and the typed fields are set to default values.
     *</p>
     */
    public void addSpice() {
        barcode = inventoryEditorLayout.editBarcode.getText().toString();
        name = inventoryEditorLayout.editSpiceName.getText().toString();
        stock = inventoryEditorLayout.editStock.getText().toString();
        container = inventoryEditorLayout.spContainerType.getSelectedItem().toString();
        brand = inventoryEditorLayout.spBrand.getSelectedItem().toString();
        receivedSpice = mySpiceDao.getSpiceByBarcode(barcode);
        if(receivedSpice != null) {
            barcodeID = receivedSpice.getBarcode();
        }
        if (barcode.isEmpty() || name.isEmpty() || stock.isEmpty() || container.isEmpty() || brand.isEmpty() || container.contains("Select One...") || brand.contains("Select One...")) {
            Toast.makeText(this, "Error - one of the boxes is empty. Please fill all details.", Toast.LENGTH_LONG).show();

        } else if (barcode.equals(barcodeID)) {
            Toast.makeText(this, barcode + " Already exists!", Toast.LENGTH_LONG).show();
        } else if (receivedSpice == null) {
            Spice spice = new Spice(barcode, name, Integer.parseInt(stock), container, brand);
            mySpiceDao.insertSpice(spice);
            Toast.makeText(this,  name + " was added to inventory!", Toast.LENGTH_SHORT).show();
            setDefaultInfo();
        }
    }

    /**
     *<p>
     *     deleteSpice() is called to remove the barcode associated spice from the inventory. The
     *     spice is searched for by the barcode number and validated to make sure it exists. If returns
     *     null then a notification is sent saying barcode not found. If not null then the delete
     *     from table is called and a notification that it was successful is shown.
     *</p>
     */
    public void deleteSpice(){
        barcode = inventoryEditorLayout.editBarcode.getText().toString();
        receivedSpice = mySpiceDao.getSpiceByBarcode(barcode);
        if (receivedSpice == null) {
            Toast.makeText(this, "Error - Barcode was not found! Please try again.", Toast.LENGTH_LONG).show();
        } else {
            mySpiceDao.deleteSpice(receivedSpice);
            Toast.makeText(this,  receivedSpice.getBarcode() +" " + receivedSpice.getSpiceName() + " was deleted!", Toast.LENGTH_SHORT).show();
            setDefaultInfo();
        }
    }

    /**
     *<p>
     *     setDefaultInfo() is called after the typed data is used to add, remove or update a Spice.
     *     This resets all the entries to blanks.
     *</p>
     */
    public void setDefaultInfo() {
        inventoryEditorLayout.editBarcode.setText("");
        inventoryEditorLayout.editSpiceName.setText("");
        inventoryEditorLayout.editStock.setText("");
        inventoryEditorLayout.spContainerType.setSelection(0);
        inventoryEditorLayout.spBrand.setSelection(0);
    }

    public void isVisible(){
        if(inventoryEditorLayout.tvInventoryInstruction.getVisibility() == VISIBLE){
            inventoryEditorLayout.tvInventoryInstruction.setVisibility(View.INVISIBLE);

        }else{
            inventoryEditorLayout.tvInventoryInstruction.setVisibility(VISIBLE);

        }
    }

}