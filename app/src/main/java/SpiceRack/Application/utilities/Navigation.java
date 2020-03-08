package SpiceRack.Application.utilities;

import android.content.Context;
import android.content.Intent;

import SpiceRack.Application.activites.HomeActivity;
import SpiceRack.Application.activites.InventoryActivity;
import SpiceRack.Application.activites.InventoryEditorActivity;
import SpiceRack.Application.login.LoginActivity;
import SpiceRack.Application.login.SignUpActivity;
import SpiceRack.Application.profile.ProfileActivity;
import SpiceRack.Application.activites.ScanActivity;
import SpiceRack.Application.activites.ShoppingListActivity;
import SpiceRack.Application.login.StartupActivity;


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

    public void inventoryEditor() {
        Intent openActivity = new Intent(context, InventoryEditorActivity.class);
        context.startActivity(openActivity);
    }

    public void homePage() {
        Intent openActivity = new Intent(context, HomeActivity.class);
        context.startActivity(openActivity);
    }

    public void signUp() {
        Intent openActivity = new Intent(context, SignUpActivity.class);
        context.startActivity(openActivity);
    }

    public void logIn() {
        Intent openActivity = new Intent(context, LoginActivity.class);
        context.startActivity(openActivity);
    }
}