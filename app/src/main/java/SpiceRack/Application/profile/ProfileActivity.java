package SpiceRack.Application.profile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import SpiceRack.Application.activites.HomeActivity;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.Application.database.User;
import SpiceRack.Application.database.UserDao;
import SpiceRack.Application.login.StartupActivity;
import SpiceRack.R;

public class ProfileActivity extends AppCompatActivity {

    EditText editUserName, editEmailAddress, editPassword, editConfirmPassword, editLoginHint;
    String userName, emailAddress, editPasswordString, editConfirmPasswordString, editLoginHintString;
    SpiceDatabase mySpiceRackDb;
    UserDao myUserDao;
    private SharedPreferences prefGet;

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

        User userFromDB = myUserDao.getUserByEmail(prefGet.getString("UserLoggedIn", "defValue"));

        editEmailAddress.setText(userFromDB.getEmailAddress());
        editUserName.setText(userFromDB.getUsername());
        editLoginHint.setText(userFromDB.getLoginHint());
    }

    public void editProfile() {
        userName = editUserName.getText().toString();
        emailAddress = editEmailAddress.getText().toString();
        editPasswordString = editPassword.getText().toString();
        editConfirmPasswordString = editConfirmPassword.getText().toString();
        editLoginHintString = editLoginHint.getText().toString();

        if (!(editConfirmPasswordString.equals(editPasswordString))){
            Toast.makeText(ProfileActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        } else if (userName.isEmpty() || emailAddress.isEmpty() || editPasswordString.isEmpty() || editConfirmPasswordString.isEmpty() || editLoginHintString.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        } else if (userName.length() <3 || emailAddress.length() <3 || editPasswordString.length() <3 || editConfirmPasswordString.length() <3 || editLoginHintString.length() <3){
            Toast.makeText(ProfileActivity.this, "Minimum length 3", Toast.LENGTH_SHORT).show();
        } else {

            User userFromDB = myUserDao.getUserByEmail(prefGet.getString("UserLoggedIn", "defValue"));
            userFromDB.setEmailAddress(editEmailAddress.getText().toString());
            userFromDB.setUsername(editUserName.getText().toString());
            userFromDB.setPassword(editPassword.getText().toString());
            userFromDB.setLoginHint(editLoginHint.getText().toString());
            myUserDao.upDate(userFromDB);

            SharedPreferences prefPut = getSharedPreferences("User", Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putString("UserLoggedIn", editEmailAddress.getText().toString());
            prefEditor.commit();

            Intent openActivity = new Intent(this, HomeActivity.class);
            startActivity(openActivity);
        }
    }

    public void deleteUserAccount(){
        User userFromDB = myUserDao.getUserByEmail(prefGet.getString("UserLoggedIn", "defValue"));
        myUserDao.deleteUser(userFromDB);

        SharedPreferences prefPut = getSharedPreferences("User", Activity.MODE_PRIVATE);
        prefPut.edit().clear().commit();

        Intent openActivity = new Intent(this, StartupActivity.class);
        startActivity(openActivity);
    }
}