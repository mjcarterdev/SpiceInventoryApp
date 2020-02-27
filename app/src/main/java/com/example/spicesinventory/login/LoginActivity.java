package com.example.spicesinventory.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText editEmailAddress, editPassword, textView;
    String emailID, emailAddress, editPasswordString, pw;
    Spice_Database mySpiceRackDb;
    UserDao myUserDao;
    Button btnLogIn;
    User tempEmail, tempPw;

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnLogIn) {
                logIn();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        btnLogIn = findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(myClick);

        editEmailAddress = findViewById(R.id.editEmailAddress);
        editPassword = findViewById(R.id.editPassword);

        mySpiceRackDb = Spice_Database.getINSTANCE(this);
        myUserDao = mySpiceRackDb.getUserDao();

    }

    public void logIn() {
        emailAddress = editEmailAddress.getText().toString();
        editPasswordString = editPassword.getText().toString();

        tempEmail = myUserDao.getUserByEmail(emailAddress);
        tempPw = myUserDao.getUserByPw(editPasswordString);

        List<User> users = myUserDao.getAllUsers();

        //displays entries
        //textView = findViewById(R.id.textView);
        /*int i;
        for (i = 0; i<users.size(); i++){
            textView.append(users.get(i).getEmailAddress());
        }*/

        if(tempEmail != null)
            emailID = tempEmail.getEmailAddress();

        if(tempPw != null)
            pw = tempPw.getPassword();

        if (emailAddress.equals(emailID) && editPasswordString.equals(pw)){
            //Toast.makeText(com.example.spicesinventory.login.LoginActivity.this, "ok", Toast.LENGTH_SHORT).show();
            myUserDao.getAllUsers();

            //store logged in user to SharedPreferences
            SharedPreferences prefPut = getSharedPreferences("User", Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putString("User logged in", emailAddress);
            prefEditor.commit();

            Intent openActivity = new Intent(this, HomeActivity.class);
            startActivity(openActivity);

        } else {
            Toast.makeText(com.example.spicesinventory.login.LoginActivity.this, "Wrong user or password", Toast.LENGTH_SHORT).show();
        }

    }

}