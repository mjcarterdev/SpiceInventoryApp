package SpiceRack.Application.utilities;

import android.content.Context;
import android.content.Intent;
import SpiceRack.Application.activites.HomeActivity;
import SpiceRack.Application.activites.InventoryActivity;
import SpiceRack.Application.activites.InventoryEditorActivity;
import SpiceRack.Application.login.LoginActivity;
import SpiceRack.Application.login.SignUpActivity;
import SpiceRack.Application.profile.ProfileActivity;
import SpiceRack.Application.activites.ShoppingListActivity;
import SpiceRack.Application.login.StartupActivity;

/**
 *<p>
 *     Navigation class is used as a utility class to move between activities within the application.
 *</p>
 *
 * @author Michael
 * @author Astrid
 * @version 1.0
 * @since 05.03.2020
 */

public class Navigation {

    private Context context;
/**
 * <p>
 *     Navigation is the constructor and takes the environment data (context) of application.
 * </p>
 */
    public Navigation(Context context) {
        this.context = context;
    }

    /**
     * <p>
     *     shoppingPage() creates an intent to move from the current context to ShoppingListActivity
     *     class.
     * </p>
     */
    public void shoppingPage() {
        Intent openActivity = new Intent(context, ShoppingListActivity.class);
        context.startActivity(openActivity);
    }
    /**
     * <p>
     *     inventoryPage() creates an intent to move from the current context to InventoryActivity
     *     class.
     * </p>
     */
    public void inventoryPage() {
        Intent openActivity = new Intent(context, InventoryActivity.class);
        context.startActivity(openActivity);
    }

    /**
     * <p>
     *     profilePage() creates an intent to move from the current context to ProfileActivity
     *     class.
     * </p>
     */

    public void profilePage() {
        Intent openActivity = new Intent(context, ProfileActivity.class);
        context.startActivity(openActivity);
    }
    /**
     * <p>
     *     logOut() creates an intent to move from the current context to StartupActivity
     *     class.
     * </p>
     */
    public void logOut() {
        Intent openActivity = new Intent(context, StartupActivity.class);
        context.startActivity(openActivity);
    }
    /**
     * <p>
     *     inventoryEditor() creates an intent to move from the current context to InventoryEditorActivity
     *     class.
     * </p>
     */
    public void inventoryEditor() {
        Intent openActivity = new Intent(context, InventoryEditorActivity.class);
        context.startActivity(openActivity);
    }
    /**
     * <p>
     *    homePage() creates an intent to move from the current context to HomeActivity class.
     * </p>
     */
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