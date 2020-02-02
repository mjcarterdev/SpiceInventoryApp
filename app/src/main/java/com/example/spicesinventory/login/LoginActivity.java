package com.example.spicesinventory.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spice_sqlite_test.R;
import com.example.spicesinventory.activites.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnLogIn:logIn();
                break;
                default:signUp();
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        Button btnLogIn = findViewById(R.id.btnLogIn);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnLogIn.setOnClickListener(myClick);
        btnSignUp.setOnClickListener(myClick);
    }

    public void logIn() {
        Intent openActivity = new Intent(this, HomeActivity.class);
        startActivity(openActivity);
    }

    public void signUp() {
        Intent openActivity = new Intent(this, HomeActivity.class);
        startActivity(openActivity);
    }
}
