package com.example.spicesinventory.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.example.spice_sqlite_test.R;
import com.example.spicesinventory.login.StartupActivity;


public class HomeActivity extends AppCompatActivity {

    private OnClickListener myClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnScan:
                    scanPage();
                    break;
                case R.id.btnInventory:
                    inventoryPage();
                    break;
                case R.id.btnShoppingList:
                    shoppingPage();
                    break;
                case R.id.btnProfile:
                    profilePage();
                    break;
                default:
                    logOut();
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


    }

    void shoppingPage() {
        Intent openActivity = new Intent(this, ShoppingListActivity.class);
        startActivity(openActivity);
    }

    void inventoryPage() {
        Intent openActivity = new Intent(this, InventoryActivity.class);
        startActivity(openActivity);
    }

    void scanPage() {
        Intent openActivity = new Intent(this, ScanActivity.class);
        startActivity(openActivity);
    }

    void profilePage() {
        Intent openActivity = new Intent(this, ProfileActivity.class);
        startActivity(openActivity);
    }

    public void logOut() {
        Intent openActivity = new Intent(this, StartupActivity.class);
        startActivity(openActivity);
    }
}

