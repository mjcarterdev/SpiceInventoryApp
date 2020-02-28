package SpiceRack.Application.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import SpiceRack.Application.activites.HomeActivity;
import SpiceRack.Application.activites.ScanActivity;
import SpiceRack.R;

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

    public void logIn() {
        Intent openActivity = new Intent(this, LoginActivity.class);
        startActivity(openActivity);
    }

    public void signUp() {
        Intent openActivity = new Intent(this, SignUpActivity.class);
        startActivity(openActivity);
    }
}
