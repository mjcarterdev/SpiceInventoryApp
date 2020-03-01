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


public class InventoryEditorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String barcode,barcodeID, name, container, brand, stock;
    SpiceDatabase mySpiceRackDb;
    SpiceDao mySpiceDao;
    Spice receivedSpice;
    InventoryEditorActivityBinding inventoryEditorLayout;
    Navigation nav;
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
                default:
                    barcode = inventoryEditorLayout.editBarcode.getText().toString();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inventoryEditorLayout = InventoryEditorActivityBinding.inflate(LayoutInflater.from(this));
        setContentView(inventoryEditorLayout.getRoot());

        mySpiceRackDb = SpiceDatabase.getINSTANCE(this);
        mySpiceDao = mySpiceRackDb.getSpiceDao();

        inventoryEditorLayout.deleteSpices.setOnClickListener(myClick);
        inventoryEditorLayout.btnaddSpice.setOnClickListener(myClick);
        inventoryEditorLayout.btnUpdateSpice.setOnClickListener(myClick);

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

    public void setDefaultInfo() {
        inventoryEditorLayout.editBarcode.setText("");
        inventoryEditorLayout.editSpiceName.setText("");
        inventoryEditorLayout.editStock.setText("");
        inventoryEditorLayout.spContainerType.setSelection(0);
        inventoryEditorLayout.spBrand.setSelection(0);
    }

}
