package com.example.spicesinventory.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spice_sqlite_test.R;
import com.example.spicesinventory.utilities.Navigation;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class HomeActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    Navigation nav;
    GestureDetector myGestureDetector;
    public static final int SWIPE_THRESHOLD = 100;
    public static final int SWIPE_VELOCITY_THRESHOLD = 100;


    private OnClickListener myClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnScan:
                    scanner(v);
                    break;
                case R.id.btnInventory:
                    nav.inventoryPage();
                    break;
                case R.id.btnShoppingList:
                    nav.shoppingPage();
                    break;
                case R.id.btnProfile:
                    nav.profilePage();
                    break;
                default:
                    nav.logOut();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        Button btnScan = findViewById(R.id.btnScan);
        Button btnInventory = findViewById(R.id.btnInventory);
        Button btnShoppingList = findViewById(R.id.btnShoppingList);
        Button btnProfile = findViewById(R.id.btnProfile);
        Button btnLogOut = findViewById((R.id.btnLogout));

        btnInventory.setOnClickListener(myClick);
        btnScan.setOnClickListener(myClick);
        btnShoppingList.setOnClickListener(myClick);
        btnProfile.setOnClickListener(myClick);
        btnLogOut.setOnClickListener(myClick);

        nav = new Navigation(this);
        myGestureDetector = new GestureDetector(this, this);
    }

       /*
    Gesture recognition to change activity
     */

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
                nav.inventoryPage();
            }else{

                nav.shoppingPage();
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
    private void onSwipeBottom() {
        Toast.makeText(this,"Swipe Bottom", Toast.LENGTH_LONG).show();
    }

    private void onSwipeTop(){
        Toast.makeText(this,"Swipe Top", Toast.LENGTH_LONG).show();
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
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                String scannedBarcode = result.getContents();
                Intent intent = new Intent(HomeActivity.this, ScanActivity.class);
                intent.putExtra("ScannedBarcode", scannedBarcode);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    /*
    Methods implemented but not required
     */

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
}

