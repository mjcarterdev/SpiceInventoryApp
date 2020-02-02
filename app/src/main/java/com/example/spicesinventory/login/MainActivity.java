package com.example.spicesinventory.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spice_sqlite_test.R;
import com.example.spicesinventory.activites.HomeActivity;
import com.example.spicesinventory.activites.InventoryActivity;
import com.example.spicesinventory.activites.ShoppingListActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ShoppingPage(View view) {
        Intent openActivity = new Intent(this, ShoppingListActivity.class);
        startActivity(openActivity);
    }

    public void HomePage(View view) {
        Intent openActivity = new Intent(this, HomeActivity.class);
        startActivity(openActivity);
    }

    public void InventoryPage(View view) {
        Intent openActivity = new Intent(this, InventoryActivity.class);
        startActivity(openActivity);
    }

}