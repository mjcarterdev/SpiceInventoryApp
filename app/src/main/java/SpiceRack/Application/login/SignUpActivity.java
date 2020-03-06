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
import SpiceRack.Application.activites.HomeActivity;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.Application.database.User;
import SpiceRack.Application.database.UserDao;
import SpiceRack.R;

public class SignUpActivity extends AppCompatActivity {

    public static final String KEY = "UserLoggedIn";
    private EditText editUserName, editEmailAddress, editPassword, editConfirmPassword, editLoginHint;
    private String userName, emailAddress, editPasswordString, editConfirmPasswordString, editLoginHintString;
    private SpiceDatabase mySpiceRackDb;
    private User user;
    private UserDao myUserDao;
    private Button btnSignUp;

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
        editLoginHint = findViewById(R.id.editLoginHint);

        mySpiceRackDb = SpiceDatabase.getINSTANCE(this);
        myUserDao = mySpiceRackDb.getUserDao();
    }

    private boolean isEmailValid(CharSequence email) {
        emailAddress = editEmailAddress.getText().toString();
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    private boolean checkIfExists(){
        emailAddress = editEmailAddress.getText().toString();
        User userFromDB = myUserDao.getUserByEmail(emailAddress);
        if (userFromDB != null) {
            if (emailAddress.equals(userFromDB.getEmailAddress())) {
                return true;
            }
        }
        return false;
    }

    private void signUp() {
        userName = editUserName.getText().toString();
        emailAddress = editEmailAddress.getText().toString();
        editPasswordString = editPassword.getText().toString();
        editConfirmPasswordString = editConfirmPassword.getText().toString();
        editLoginHintString = editLoginHint.getText().toString();

        user = new User(userName, emailAddress, editPasswordString, editLoginHintString);

        if (!(editConfirmPasswordString.equals(editPasswordString))){
            Toast.makeText(SignUpActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        } else if (userName.isEmpty() || emailAddress.isEmpty() || editPasswordString.isEmpty() || editConfirmPasswordString.isEmpty() || editLoginHintString.isEmpty()){
            Toast.makeText(SignUpActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        } else if (!isEmailValid(emailAddress)) {
            Toast.makeText(SignUpActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        } else if (userName.length() <3) {
            Toast.makeText(SignUpActivity.this, "Username minimum length 3", Toast.LENGTH_SHORT).show();
        } else if (editPasswordString.length() <6 || editConfirmPasswordString.length() <6){
            Toast.makeText(SignUpActivity.this, "Password minimum length 6", Toast.LENGTH_SHORT).show();
        } else if (editLoginHintString.length() <3){
            Toast.makeText(SignUpActivity.this, "Hint minimum length 3", Toast.LENGTH_SHORT).show();
        } else if (checkIfExists()){
            Toast.makeText(SignUpActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
        } else {

            myUserDao.insertUser(user);

            Intent openActivity = new Intent(this, HomeActivity.class);
            startActivity(openActivity);

            SharedPreferences prefPutU = getSharedPreferences("User", Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditorU = prefPutU.edit();
            prefEditorU.putString(KEY, emailAddress);
            prefEditorU.commit();
        }
    }
}

