package SpiceRack.Application.profile;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import SpiceRack.Application.activites.AboutActivity;
import SpiceRack.Application.activites.HomeActivity;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.Application.database.User;
import SpiceRack.Application.database.UserDao;
import SpiceRack.Application.utilities.Navigation;
import SpiceRack.R;

/**
 *  <p>
 *  ProfileActivity class creates and displays the user profile. The logged in user can either update
 *  or delete their user account.
 *  </p>
 *
 *  @author Michael
 *  @author Astrid
 *  @version 1.0
 *  @since 05.03.2020
 */

import static android.view.View.VISIBLE;

public class ProfileActivity extends AppCompatActivity {

    public static final String KEY = "UserLoggedIn";
    private EditText editUserName, editEmailAddress, editPassword, editConfirmPassword, editLoginHint;
    private String userName, emailAddress, editPasswordString, editConfirmPasswordString, editLoginHintString;
    private SpiceDatabase mySpiceRackDb;
    private UserDao myUserDao;
    private SharedPreferences prefGet;
    private Navigation nav;
    private GestureDetectorCompat myGesture;

    /**
     *  <p>
     *  Implements the OnClickListener. If a button is clicked, the appropriate method is called.
     *  </p>
     */
    private View.OnClickListener myClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (v.getId() == R.id.btnEditProfile) {
                editProfile();
            }else if (v.getId() == R.id.btnDeleteUser){
                deleteUserAccount();
            }else if(v.getId() == R.id.ibInformation){
                isVisible();
            }else{
                Intent intent = new Intent(ProfileActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        }
    };

    /**
     *  <p>
     *  isEmailValid() checks if the characters entered into the editEmailAddress field
     *  are a valid email address.
     *  </p>
     */
    private boolean isEmailValid(CharSequence email) {
        emailAddress = editEmailAddress.getText().toString();
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    /**
     *  <p>
     *  onCreate() initializes the ProfileActivity. It sets the layout, OnClickListeners, navigation and instantiates the
     *  database.
     *  </p>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        Button btnEditProfile = findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(myClick);
        ImageButton btnInformation = findViewById(R.id.ibInformation);
        btnInformation.setOnClickListener(myClick);
        ImageButton btnAbout = findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(myClick);
        Button btnDeleteUser = findViewById(R.id.btnDeleteUser);
        btnDeleteUser.setOnClickListener(myClick);

        editUserName = findViewById(R.id.editUserName);
        editEmailAddress = findViewById(R.id.editEmailAddress);
        editPassword = findViewById(R.id.editPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);
        editLoginHint = findViewById(R.id.editLoginHint);

        mySpiceRackDb = SpiceDatabase.getINSTANCE(this);
        myUserDao = mySpiceRackDb.getUserDao();

        prefGet = getSharedPreferences("User", Activity.MODE_PRIVATE);

        User userFromDB = myUserDao.getUserByEmail(prefGet.getString(KEY, "defValue"));

        editEmailAddress.setText(userFromDB.getEmailAddress());
        editUserName.setText(userFromDB.getUsername());
        editLoginHint.setText(userFromDB.getLoginHint());

        nav = new Navigation(this);
        myGesture = new GestureDetectorCompat(this, new MyGestureListener());
    }

    /**
     *  <p>
     *  editProfile() is called to modify an entry from the User table. It performs the checks listed below
     *  and displays error messages in a toast if needed:
     *  <li>
     *      <ul>Ensures that password and confirm password are equal</ul>
     *      <ul>Ensures that all fields in the form contain data</ul>
     *      <ul>Verifies that the email address is valid</ul>
     *      <ul>Verifies that username and hint contain minimum 3 characters</ul>
     *      <ul>Verifies that the password contains minimum 6 characters</ul>
     *  </li>
     *  </p>
     *  <p>
     *      After performing the checks above and no errors occur:
     *  <li>
     *      <ul>The database table User is updated with the data entered into the form.</ul>
     *      <ul>The screen continues to the next page, the homePage</ul>
     *      <ul>The email address entered is stored into Shared Preferences. This is needed when reopening the app
     *      in order to log in a user automatically.</ul>
     *  </li>
     *  </p>
     */
    private void editProfile() {
        userName = editUserName.getText().toString();
        emailAddress = editEmailAddress.getText().toString();
        editPasswordString = editPassword.getText().toString();
        editConfirmPasswordString = editConfirmPassword.getText().toString();
        editLoginHintString = editLoginHint.getText().toString();

        if (!(editConfirmPasswordString.equals(editPasswordString))){
            Toast.makeText(ProfileActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        } else if (userName.isEmpty() || emailAddress.isEmpty() || editPasswordString.isEmpty() || editConfirmPasswordString.isEmpty() || editLoginHintString.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        } else if (userName.length() <3) {
            Toast.makeText(ProfileActivity.this, "Username minimum length 3", Toast.LENGTH_SHORT).show();
        } else if (editPasswordString.length() <6 || editConfirmPasswordString.length() <6){
            Toast.makeText(ProfileActivity.this, "Password minimum length 6", Toast.LENGTH_SHORT).show();
        } else if (editLoginHintString.length() <3){
            Toast.makeText(ProfileActivity.this, "Hint minimum length 3", Toast.LENGTH_SHORT).show();
        } else if (!isEmailValid(emailAddress)) {
            Toast.makeText(ProfileActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        } else {

            User userFromDB = myUserDao.getUserByEmail(prefGet.getString(KEY, "defValue"));
            userFromDB.setEmailAddress(editEmailAddress.getText().toString());
            userFromDB.setUsername(editUserName.getText().toString());
            userFromDB.setPassword(editPassword.getText().toString());
            userFromDB.setLoginHint(editLoginHint.getText().toString());
            myUserDao.upDate(userFromDB);

            SharedPreferences prefPut = getSharedPreferences("User", Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putString(KEY, editEmailAddress.getText().toString());
            prefEditor.commit();

            nav.homePage();
        }
    }

    /**
     *  <p>
     *  deleteUserAccount() is called to delete a user object from the User table. Additionally, the
     *  email address is removed from SharedPreferences in order to avoid login errors. The navigation
     *  moves to the next screen, the StartupActivity.
     *  </p>
     */
    private void deleteUserAccount(){
        User userFromDB = myUserDao.getUserByEmail(prefGet.getString(KEY, "defValue"));
        myUserDao.deleteUser(userFromDB);

        SharedPreferences prefPut = getSharedPreferences("User", Activity.MODE_PRIVATE);
        prefPut.edit().clear().apply();

        nav.logOut();
    }

    /**
     *  <p>
     *  The user can also swipe right in order to return to the homepage.
     *  </p>
     */
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
            float diffY = moveEvent.getY() - downEvent.getY();
            float diffX = moveEvent.getX() - downEvent.getX();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        nav.homePage();
                    }
                }
            }
            return true;
        }
    }

    public void isVisible(){
        TextView instruction = findViewById(R.id.tvProfileInstruction);
        if(instruction.getVisibility() == VISIBLE){
            instruction.setVisibility(View.INVISIBLE);
        }else{
            instruction.setVisibility(VISIBLE);

        }
    }
}