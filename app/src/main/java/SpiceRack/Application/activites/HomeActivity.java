package SpiceRack.Application.activites;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static android.view.View.VISIBLE;

/**
 * <h1>Home Activity</h1>
 * <p>  The Home Activity is the main page to navigate through all the various application features.
 *      OnSwipe gestures are implemented along with buttons to provide multiple means of transitioning
 *      though the application.
 * </p>
 *
 * @author Michael
 * @author Astrid
 * @version 1.0
 * @since 05.03.2020
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
/**
 * <p>
 *      Global variables for storing a view to pass into the scanner() method and storing a
 *      navigation object to simplify moving around the application. The private GestureDetectorCompat
 *      object is used to detect swipe movements and run code blocks depending on the type of swipe.
 * </p>
 */

    View v;
    Navigation nav;
    private GestureDetectorCompat myGesture;
    HomeActivityBinding homeLayout;

    /**
     * Class implements onClickListener method onClick(). A switch case is used to identify which
     * button has been pressed. Each case will perform a navigation to another activity except the
     * "Scan" button which called the scanner() method.
     * @param v is the view that has been clicked and is passed to the onClick method.
     * @link scanner().
     * @link Navigation class.
     */
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
            case R.id.ibInformation:
                isVisible();
                break;
            default:
                logOut();
                nav.logOut();
        }
    }

    /**
     * isVisible() is used to check the state of the instruction views. If they are visible when the
     * method is called they will become invisible. If they are invisible they will become visible.
     */
    public void isVisible(){
        if(homeLayout.tvInventoryListInstruction.getVisibility() == VISIBLE){
            homeLayout.tvInventoryListInstruction.setVisibility(View.INVISIBLE);
            homeLayout.tvProfileInstruction.setVisibility(View.INVISIBLE);
            homeLayout.tvScanInstruction.setVisibility(View.INVISIBLE);
            homeLayout.tvShoppingListInstruction.setVisibility(View.INVISIBLE);
        }else{
            homeLayout.tvInventoryListInstruction.setVisibility(VISIBLE);
            homeLayout.tvProfileInstruction.setVisibility(View.VISIBLE);
            homeLayout.tvScanInstruction.setVisibility(View.VISIBLE);
            homeLayout.tvShoppingListInstruction.setVisibility(View.VISIBLE);
        }
    }
    /**
     * <p>LogOut() calls the shared preference for the user and clears the data saved upon logging
     * out of the home activity.</p>
     */
    public void logOut() {
        SharedPreferences prefPut = getSharedPreferences("User", Activity.MODE_PRIVATE);
        prefPut.edit().clear().apply();
    }

    /**
     * <p>onCreate() creates a navigation object and gesture listener. Sets views for a onClickListener.
     * @param savedInstanceState saved instance state of the activity.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeLayout = HomeActivityBinding.inflate(LayoutInflater.from(this));
        setContentView(homeLayout.getRoot());

        homeLayout.btnInventory.setOnClickListener(this);
        homeLayout.btnScan.setOnClickListener(this);
        homeLayout.btnShoppingList.setOnClickListener(this);
        homeLayout.btnProfile.setOnClickListener(this);
        homeLayout.btnLogout.setOnClickListener(this);
        homeLayout.ibInformation.setOnClickListener(this);

        nav = new Navigation(this);
        myGesture = new GestureDetectorCompat(this, new MyGestureListener());
    }

    /**
     * Analyzes the given motion event and if applicable triggers the appropriate callbacks on the
     * GestureDetector.OnGestureListener supplied.
     * @param event the current motion event
     * @return true if the GestureDetector.OnGestureListener consumed the event, else false.
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.myGesture.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * MyGestureListener class extends GestureDetector.SimpleOnGestureListener a convenient class
     * where only a subset of gestures are required. This implements onDown() and onFLing() methods
     * but does nothing and return false.
     */

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        /**
         * <p>Swipe_Threshold is the distance travelled from the initial screen contact till the
         * final position in any of the four directions.
         * </p>
         * <p>Swipe_Velocity_Threshold is the speed at which a movement needs to travel before being
         * identified as a gesture.</p>
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
     * Copyright 2009 ZXing authors
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *      http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */

     /**    <p> A utility class which helps ease integration with Barcode Scanner via Intent's.
      *     This is a simple way to invoke barcode scanning and receive the result, without any
      *     need to integrate, modify, or learn the project's source code.</p>
      *
      *     <h1>Initiating a barcode scan</h1>
      *
      *     <p> To integrate, create an instance of IntentIntegrator and call initiateScan() which
      *     can recognise all barcode types and wait for the result in your app.</p>
      *
      *     <href>https://github.com/zxing/zxing</href>
      *     <href>http://javadox.com/com.google.zxing/android-integration/3.1.0/com/google/zxing/integration/android/IntentIntegrator.html</href>
      *
      *     @author Sean Owen
      *     @author Fred Lin
      *     @author Isaac Potoczny-Jones
      *     @author Brad Drehmer
      *     @author gcstang
      */


    public void scanner(View v) {
            new IntentIntegrator(this).initiateScan();
        }

    /**
     *  <p>
     *      onActivityResult() creates IntentResult that encapsulates the result of a barcode scan
     *      invoked through IntentIntegrator. If the result is null the method is called again.
     *      If the scan is called but no data is captured before closing, a Toast message is displayed
     *      stating that the process was cancelled. If data is captured by the scanning event the content
     *      of the result (i.e. barcode number) is stored as a String and passed to scan_activity class
     *      through an intent.
     *  </p>
     *  <href>http://javadox.com/com.google.zxing/android-integration/3.1.0/com/google/zxing/integration/android/IntentResult.html</href>
     *
     *  @param requestCode static int associated with the scanning process. Code from {@code onActivityResult()}
     *  @param resultCode static int associated with the scanning process. Code from {@code onActivityResult()}
     *  @param data the intent call from the activity to start the barcode scan.
     *
     *  @author Sean Owen
     */

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