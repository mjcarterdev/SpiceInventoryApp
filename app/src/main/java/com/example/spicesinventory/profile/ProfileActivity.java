package com.example.spicesinventory.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spice_sqlite_test.R;
import com.example.spicesinventory.activites.HomeActivity;
import com.example.spicesinventory.database.Spice_Database;
import com.example.spicesinventory.database.User;
import com.example.spicesinventory.database.UserDao;
import com.example.spicesinventory.login.LoginActivity;

public class ProfileActivity extends AppCompatActivity {

    EditText editUserName, editEmailAddress, editPassword, editConfirmPassword;
    String userName, emailAddress, editPasswordString, editConfirmPasswordString;
    Spice_Database mySpiceRackDb;
    User user;
    UserDao myUserDao;
    //Button btnEditProfile;
    User tempUser, tempEmail, tempPw, tempConfirmPw;

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnEditProfile) {
                editProfile();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        Button btnEditProfile = findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(myClick);

        editUserName = findViewById(R.id.editUserName);
        editEmailAddress = findViewById(R.id.editEmailAddress);
        editPassword = findViewById(R.id.editPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);

        mySpiceRackDb = Spice_Database.getINSTANCE(this);
        myUserDao = mySpiceRackDb.getUserDao();

        myUserDao.getAllUsers();
    }

    public void editProfile() {
        //Toast.makeText(this,"edit profile pressed",Toast.LENGTH_LONG).show();

        userName = editUserName.getText().toString();
        emailAddress = editEmailAddress.getText().toString();
        editPasswordString = editPassword.getText().toString();
        editConfirmPasswordString  = editConfirmPassword.getText().toString();

        tempUser = myUserDao.getUserByUser(userName);
        tempEmail = myUserDao.getUserByEmail(emailAddress);
        tempPw = myUserDao.getUserByPw(editPasswordString);

        user = new User(userName, emailAddress, editPasswordString);

        if (!(editConfirmPasswordString.equals(editPasswordString))){
            Toast.makeText(ProfileActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        } else {

            //insert(userName, emailAddress, editPasswordString);
            myUserDao.upDate(user);

            Intent openActivity = new Intent(this, HomeActivity.class);
            startActivity(openActivity);
        }

    }

}