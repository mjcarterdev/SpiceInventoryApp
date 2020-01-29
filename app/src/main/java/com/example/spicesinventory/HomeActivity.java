package com.example.spicesinventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.spice_sqlite_test.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class HomeActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    EditText Barcode, SpiceName, Stock, updateStock;
    TextView BarcodeShow, NameShow, StockShow;
    myDbController database;
    GestureDetector myGestureDetector;
    public static final int SWIPE_THRESHOLD = 100;
    public static final int SWIPE_VELOCITY_THRESHOLD = 100;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        Barcode= findViewById(R.id.editBarcode);
        SpiceName= findViewById(R.id.editSpiceName);
        Stock= findViewById(R.id.editStock);
        BarcodeShow = findViewById(R.id.BarcodeShow);
        NameShow = findViewById(R.id.NameShow);
        StockShow = findViewById(R.id.StockShow);
        updateStock = findViewById(R.id.updateStock);

        database = new myDbController(this);
        myGestureDetector = new GestureDetector(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        myGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
        boolean result = false;
        float diffY = moveEvent.getY() - downEvent.getY();
        float diffX = moveEvent.getX() - downEvent.getX();

        if(Math.abs(diffX)> Math.abs(diffY)){
            //right or left swipe
            if(Math.abs(diffX)> SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD){
                if(diffX > 0){
                    InventoryPage(view);
                }else{

                    ShoppingPage(view);
                }
                result = true;
            }
        }else{
            //up or down swipe
            if (Math.abs(diffY)> SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD){
                if(diffY > 0){
                    onSwipeBottom();
                }else{
                    onSwipeTop();
                }
                result = true;
            }
        }

        return result;
    }

    public void addSpice(View view) {
        String barcodeNumber = Barcode.getText().toString();
        String spiceName = SpiceName.getText().toString();
        int stock = Integer.parseInt(Stock.getText().toString());

        if(barcodeNumber.isEmpty() || spiceName.isEmpty() || stock == 0)
        {
            Toast.makeText(this,"fill in all boxes",Toast.LENGTH_LONG).show();
        }else{
            long id = database.insertData(barcodeNumber, spiceName, stock);
            if(id <= 0){
                Toast.makeText(this, "Insertion unsuccessful", Toast.LENGTH_LONG).show();
                Barcode.setText("");
                SpiceName.setText("");
                Stock.setText("");
            }else{
                Toast.makeText(this, "Insertion successful", Toast.LENGTH_LONG).show();
                Barcode.setText("");
                SpiceName.setText("");
                Stock.setText("");

            }
        }
    }

    public void deleteSpice(View view) {
        String toDelete = Barcode.getText().toString();
        if(toDelete.isEmpty())
        {
            Toast.makeText(this, "Enter Spice Name", Toast.LENGTH_LONG).show();
        }
        else{
            int a= database.delete(toDelete);
            if(a<=0)
            {

                Toast.makeText(this, toDelete+" Deletion was unsuccessful", Toast.LENGTH_LONG).show();
                SpiceName.setText("");
            }
            else
            {
                Toast.makeText(this, toDelete+" was Deleted", Toast.LENGTH_LONG).show();
                SpiceName.setText("");

            }
        }
    }

    public void updateSpice(View view) {
        String toUpdate = Barcode.getText().toString();
        int newStock = Integer.parseInt(updateStock.getText().toString());
        if(toUpdate.isEmpty())
        {
            Toast.makeText(this, "Enter Barcode", Toast.LENGTH_LONG).show();
        }
        else{
            int a= database.updateStock(toUpdate, newStock);
            if(a<=0)
            {
                Toast.makeText(this, toUpdate+" update was unsuccessful", Toast.LENGTH_LONG).show();
                SpiceName.setText("");
            }
            else
            {
                Toast.makeText(this, toUpdate+" was Updated", Toast.LENGTH_LONG).show();
                SpiceName.setText("");

            }
        }
    }

    public void scanner(View v) {
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Barcode.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void ShoppingPage(View view) {
        Intent openActivity = new Intent(this, ShoppingListActivity.class);
        startActivity(openActivity);
    }

    public void InventoryPage(View view) {
        Intent openActivity = new Intent(this, InventoryActivity.class);
        startActivity(openActivity);
    }



    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    private void onSwipeBottom() {
        Toast.makeText(this,"Swipe Bottom", Toast.LENGTH_LONG).show();
    }

    private void onSwipeTop(){
        Toast.makeText(this,"Swipe Top", Toast.LENGTH_LONG).show();
    }

}