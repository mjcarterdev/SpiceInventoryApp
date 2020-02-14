package com.example.spicesinventory.utilities;

import android.content.Context;
import android.content.Intent;

import com.example.spicesinventory.activites.InventoryActivity;
import com.example.spicesinventory.profile.ProfileActivity;
import com.example.spicesinventory.activites.ScanActivity;
import com.example.spicesinventory.activites.ShoppingListActivity;
import com.example.spicesinventory.login.StartupActivity;


public class Navigation {

    private Context context;

    public Navigation(Context context) {
        this.context = context;
    }

    public void shoppingPage() {
        Intent openActivity = new Intent(context, ShoppingListActivity.class);
        context.startActivity(openActivity);
    }

    public void inventoryPage() {
        Intent openActivity = new Intent(context, InventoryActivity.class);
        context.startActivity(openActivity);
    }

     public void scanPage() {
        Intent openActivity = new Intent(context, ScanActivity.class);
        context.startActivity(openActivity);
}

    public void profilePage() {
        Intent openActivity = new Intent(context, ProfileActivity.class);
        context.startActivity(openActivity);
    }

    public void logOut() {
        Intent openActivity = new Intent(context, StartupActivity.class);
        context.startActivity(openActivity);
    }

}
