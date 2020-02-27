package SpiceRack.Application.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import SpiceRack.Application.database.Spice;
import SpiceRack.Application.database.SpiceDao;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.R;


public class InventoryEditorActivity extends AppCompatActivity {
    EditText editBarcode, editName, editStock, editContainerType, editBrand;
    Button deleteSpice, insertSpice;
    String barcode,barcodeID, name, stock, container, brand;
    SpiceDatabase mySpiceRackDb;
    SpiceDao mySpiceDao;
    Spice temp;


    private View.OnClickListener myClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.deleteSpices:
                    deleteSpice();
                    break;
                case R.id.btnaddSpiceInventory:
                    addSpice();
                    break;
                default:
                    barcode = editBarcode.getText().toString();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_editor_activity);
        editBarcode = findViewById(R.id.editBarcode);
        editName = findViewById(R.id.editSpiceName);
        editStock = findViewById(R.id.tvStock);
        deleteSpice = findViewById(R.id.deleteSpices);
        insertSpice = findViewById(R.id.btnaddSpiceInventory);
        editContainerType = findViewById(R.id.editContainerType);
        editBrand = findViewById(R.id.editBrand);

        mySpiceRackDb = SpiceDatabase.getINSTANCE(this);
        mySpiceDao = mySpiceRackDb.getSpiceDao();

        deleteSpice.setOnClickListener(myClick);
        insertSpice.setOnClickListener(myClick);

        Intent incomingIntent = getIntent();
        String incomingBarcode = incomingIntent.getStringExtra("ScannedBarcode");
        editBarcode.setText(incomingBarcode);
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
        barcode = editBarcode.getText().toString();
        name = editName.getText().toString();
        stock = editStock.getText().toString();
        container = editContainerType.getText().toString();
        brand = editBrand.getText().toString();
        temp = mySpiceDao.getSpiceByBarcode(barcode);
        if(temp != null)
            barcodeID = temp.getBarcode();


        if (barcode.isEmpty() || name.isEmpty() || stock.isEmpty()) {
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
        barcode = editBarcode.getText().toString();
        temp = mySpiceDao.getSpiceByBarcode(barcode);
        if (temp == null) {
            Toast.makeText(this, "Error - Barcode was not found! Please try again.", Toast.LENGTH_LONG).show();
        } else {
            delete(barcode);
            setDefaultInfo();
        }
    }

    public void setDefaultInfo() {
        editBarcode.setText("");
        editName.setText("");
        editStock.setText("");
        editContainerType.setText("");
        editBrand.setText("");

    }
}
