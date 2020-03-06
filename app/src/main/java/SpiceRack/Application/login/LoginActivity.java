package SpiceRack.Application.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import SpiceRack.Application.activites.HomeActivity;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.Application.database.User;
import SpiceRack.Application.database.UserDao;
import SpiceRack.R;

public class LoginActivity extends AppCompatActivity {

    public static final String KEY = "UserLoggedIn";
    private EditText editEmailAddress, editPassword;
    private String emailID, emailAddress, editPasswordString, pw;
    private SpiceDatabase mySpiceRackDb;
    private UserDao myUserDao;
    private Button btnLogIn, btnShowHint;
    private User tempEmail, tempPw;

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnLogIn) {
                logIn();
            } else if (v.getId() == R.id.btnShowHint) {
                showHint();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        btnLogIn = findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(myClick);

        btnShowHint = findViewById(R.id.btnShowHint);
        btnShowHint.setOnClickListener(myClick);

        editEmailAddress = findViewById(R.id.editEmailAddress);
        editPassword = findViewById(R.id.editPassword);

        mySpiceRackDb = SpiceDatabase.getINSTANCE(this);
        myUserDao = mySpiceRackDb.getUserDao();
    }

    private void showHint(){
        emailAddress = editEmailAddress.getText().toString();
        tempEmail = myUserDao.getUserByEmail(emailAddress);

        if(tempEmail != null){
            Toast.makeText(LoginActivity.this, tempEmail.getLoginHint(), Toast.LENGTH_SHORT).show();
        }
    }

    private void logIn() {
        emailAddress = editEmailAddress.getText().toString();
        editPasswordString = editPassword.getText().toString();

        tempEmail = myUserDao.getUserByEmail(emailAddress);
        tempPw = myUserDao.getUserByPw(editPasswordString);

        List<User> users = myUserDao.getAllUsers();

        if(tempEmail != null)
            emailID = tempEmail.getEmailAddress();

        if(tempPw != null)
            pw = tempPw.getPassword();

        if (emailAddress.equals(emailID) && editPasswordString.equals(pw)){

            myUserDao.getAllUsers();

            //store logged in user to SharedPreferences
            SharedPreferences prefPut = getSharedPreferences("User", Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putString(KEY, emailAddress);
            prefEditor.commit();

            Intent openActivity = new Intent(this, HomeActivity.class);
            startActivity(openActivity);

        } else {
            Toast.makeText(LoginActivity.this, "Wrong user or password", Toast.LENGTH_SHORT).show();
        }
    }
}