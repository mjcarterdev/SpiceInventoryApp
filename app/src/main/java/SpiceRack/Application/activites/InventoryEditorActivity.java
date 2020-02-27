package SpiceRack.Application.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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


public class InventoryEditorActivity extends AppCompatActivity {
    String barcode,barcodeID, name, stock, container, brand;
    SpiceDatabase mySpiceRackDb;
    SpiceDao mySpiceDao;
    Spice temp;
    InventoryEditorActivityBinding inventoryEditorLayout;
    Navigation nav;
    private GestureDetectorCompat myGesture;

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

    public void insert(String barcode, String name, String stock, String containerType, String brand) {
        Spice spice = new Spice(barcode, name, stock, containerType, brand);
        mySpiceDao.insertSpice(spice);
    }

    public void delete(String barcode) {
        Spice toDelete = mySpiceDao.getSpiceByBarcode(barcode);
        mySpiceDao.deleteSpice(toDelete);
    }


    public void addSpice() {
        barcode = inventoryEditorLayout.editBarcode.getText().toString();
        name = inventoryEditorLayout.editSpiceName.getText().toString();
        stock = inventoryEditorLayout.editStock.getText().toString();
        container = inventoryEditorLayout.editContainerType.getText().toString();
        brand = inventoryEditorLayout.editBrand.getText().toString();
        temp = mySpiceDao.getSpiceByBarcode(barcode);
        if(temp != null)
            barcodeID = temp.getBarcode();

        if (barcode.isEmpty() || name.isEmpty() || stock.isEmpty() || container.isEmpty() || brand.isEmpty()) {
            Toast.makeText(this, "Error - one of the boxes is empty. Please fill all details.", Toast.LENGTH_LONG).show();

        } else if (barcode.equals(barcodeID)) {
            Toast.makeText(this, barcode + " Already exists " + barcodeID, Toast.LENGTH_LONG).show();
        }
        else if (temp == null) {
            insert(barcode, name, stock, container, brand);
            setDefaultInfo();
        }
    }

    public void deleteSpice(){
        barcode = inventoryEditorLayout.editBarcode.getText().toString();
        temp = mySpiceDao.getSpiceByBarcode(barcode);
        if (temp == null) {
            Toast.makeText(this, "Error - Barcode was not found! Please try again.", Toast.LENGTH_LONG).show();
        } else {
            delete(barcode);
            setDefaultInfo();
        }
    }

    public void setDefaultInfo() {
        inventoryEditorLayout.editBarcode.setText("");
        inventoryEditorLayout.editSpiceName.setText("");
        inventoryEditorLayout.editStock.setText("");
        inventoryEditorLayout.editContainerType.setText("");
        inventoryEditorLayout.editBrand.setText("");
    }
}
