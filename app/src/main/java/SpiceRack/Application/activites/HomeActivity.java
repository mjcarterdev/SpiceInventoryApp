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
import SpiceRack.R;
import SpiceRack.Application.utilities.Navigation;
import SpiceRack.databinding.HomeActivityBinding;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 *
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    View v;
    Navigation nav;
    private GestureDetectorCompat myGesture;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeActivityBinding homeLayout = HomeActivityBinding.inflate(LayoutInflater.from(this));
        setContentView(homeLayout.getRoot());

        homeLayout.btnInventory.setOnClickListener(this);
        homeLayout.btnScan.setOnClickListener(this);
        homeLayout.btnShoppingList.setOnClickListener(this);
        homeLayout.btnProfile.setOnClickListener(this);
        homeLayout.btnLogout.setOnClickListener(this);

        nav = new Navigation(this);
        myGesture = new GestureDetectorCompat(this, new MyGestureListener());
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
            boolean result = false;
            float diffY = moveEvent.getY() - downEvent.getY();
            float diffX = moveEvent.getX() - downEvent.getX();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                //right or left swipe
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        nav.inventoryPage();
                    } else {
                        nav.shoppingPage();
                    }
                }
            } else {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        scanner(v);
                    } else {
                        nav.profilePage();
                    }
                    result = true;
                }
                return result;
            }
            return false;
        }
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
                    String scannedBarcode = result.getContents();
                    Intent intent = new Intent(HomeActivity.this, ScanActivity.class);
                    intent.putExtra("ScannedBarcode", scannedBarcode);
                    startActivity(intent);
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

    }