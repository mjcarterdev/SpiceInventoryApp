package com.example.spicesinventory.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spice_sqlite_test.R;
import com.example.spicesinventory.login.StartupActivity;

public class ProfileActivity extends AppCompatActivity {

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnEditProfile) {
                editProfile();
            } else {
                logOut();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        Button btnEditProfile = findViewById(R.id.btnEditProfile);
        Button btnLogout = findViewById(R.id.btnLogout);

        btnEditProfile.setOnClickListener(myClick);
        btnLogout.setOnClickListener(myClick);
    }

    public void editProfile() {
        Toast.makeText(this,"edit profile pressed",Toast.LENGTH_LONG).show();
    }

    public void logOut(){
        Intent openActivity = new Intent(this, StartupActivity.class);
        startActivity(openActivity);
    }

}
