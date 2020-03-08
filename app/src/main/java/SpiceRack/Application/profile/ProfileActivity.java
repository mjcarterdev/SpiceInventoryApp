package SpiceRack.Application.profile;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.Application.database.User;
import SpiceRack.Application.database.UserDao;
import SpiceRack.Application.utilities.Navigation;
import SpiceRack.R;

public class ProfileActivity extends AppCompatActivity {

    public static final String KEY = "UserLoggedIn";
    private EditText editUserName, editEmailAddress, editPassword, editConfirmPassword, editLoginHint;
    private String userName, emailAddress, editPasswordString, editConfirmPasswordString, editLoginHintString;
    private SpiceDatabase mySpiceRackDb;
    private UserDao myUserDao;
    private SharedPreferences prefGet;
    private Navigation nav;
    private GestureDetectorCompat myGesture;

    private View.OnClickListener myClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (v.getId() == R.id.btnEditProfile) {
                editProfile();
            }else if (v.getId() == R.id.btnDeleteUser){
                deleteUserAccount();
            }
        }
    };

    private boolean isEmailValid(CharSequence email) {
        emailAddress = editEmailAddress.getText().toString();
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        Button btnEditProfile = findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(myClick);

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

    private void deleteUserAccount(){
        User userFromDB = myUserDao.getUserByEmail(prefGet.getString(KEY, "defValue"));
        myUserDao.deleteUser(userFromDB);

        SharedPreferences prefPut = getSharedPreferences("User", Activity.MODE_PRIVATE);
        prefPut.edit().clear().commit();

        nav.logOut();
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
            float diffY = moveEvent.getY() - downEvent.getY();
            float diffX = moveEvent.getX() - downEvent.getX();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                //right or left swipe
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        nav.homePage();
                    }
                }
            }
            return true;
        }
    }
}