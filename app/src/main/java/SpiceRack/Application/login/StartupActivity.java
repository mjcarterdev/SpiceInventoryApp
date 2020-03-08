package SpiceRack.Application.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import SpiceRack.Application.activites.HomeActivity;
import SpiceRack.R;

/**
 * <h1>SpiceRack</h1>
 * <p>
 *      The SpiceRack application is a database management system for spices and herbs within
 *      the kitchen. It is intended for home personal use and has additional features for building
 *      simple shopping lists.
 *</p>
 * <p>
 *      The startup activity class is the first activity to be initiated upon opening of a new
 *      instance of the app. The user has two onclick buttons to either move to new user entry
 *      or to log in.
 * </p>
 * @author michael and astrid
 * @version 1.0
 * @since 05.03.2020
 */
public class StartupActivity extends AppCompatActivity {

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnLogIn) {
                logIn();
            } else {
                signUp();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_activity);
        Button btnLogIn = findViewById(R.id.btnLogIn);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnLogIn.setOnClickListener(myClick);
        btnSignUp.setOnClickListener(myClick);
    }

    /**
     * The logIn method is called when the Log In button is clicked. Previous users login data is
     * stored as a shared preference. If data is found, the method will move straight to the home
     * activity. Otherwise, it will direct the user to the Log In activity.
     */
    public void logIn() {
        SharedPreferences prefGet = getSharedPreferences("User", Activity.MODE_PRIVATE);

        if (prefGet.contains("UserLoggedIn")) {
            Intent openActivity = new Intent(this, HomeActivity.class);
            startActivity(openActivity);
        } else {
            Intent openActivity = new Intent(this, LoginActivity.class);
            startActivity(openActivity);
        }
    }

    /**
     * The signUp method is called when Sign Up button is clicked. It moves the user to the SignUp
     * activity.
     */
    public void signUp() {
        Intent openActivity = new Intent(this, SignUpActivity.class);
        startActivity(openActivity);
    }
}
