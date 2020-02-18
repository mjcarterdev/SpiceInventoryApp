package com.example.spicesinventory.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spice_sqlite_test.R;
import com.example.spicesinventory.activites.HomeActivity;
import com.example.spicesinventory.database.User;
import com.example.spicesinventory.database.UserDao;
import com.example.spicesinventory.database.Spice_Database;

public class SignUpActivity extends AppCompatActivity {

    EditText editUserName, editEmailAddress, editPassword, editConfirmPassword;
    String userName, emailAddress, editPasswordString, editConfirmPasswordString;
    Spice_Database mySpiceRackDb;
    User user;
    UserDao myUserDao;
    Button btnSignUp;

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnSignUp) {
                signUp();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(myClick);

        editUserName = findViewById(R.id.editUserName);
        editEmailAddress = findViewById(R.id.editEmailAddress);
        editPassword = findViewById(R.id.editPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);

        mySpiceRackDb = Spice_Database.getINSTANCE(this);
        myUserDao = mySpiceRackDb.getUserDao();

    }

    public void signUp() {
        user = new User(userName, emailAddress, editPasswordString);

        userName = editUserName.getText().toString();
        emailAddress = editEmailAddress.getText().toString();
        editPasswordString = editPassword.getText().toString();
        editConfirmPasswordString = editConfirmPassword.getText().toString();

        if (!(editConfirmPasswordString.equals(editPasswordString))){
            Toast.makeText(SignUpActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        }else {

            myUserDao.insertUser(user);

            Intent openActivity = new Intent(this, HomeActivity.class);
            startActivity(openActivity);
        }
    }

}
