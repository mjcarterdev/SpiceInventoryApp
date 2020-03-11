package SpiceRack.Application.login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.Application.database.User;
import SpiceRack.Application.database.UserDao;
import SpiceRack.Application.utilities.Navigation;
import SpiceRack.R;

    /**
     *  <p>
     *  SignUpActivity class creates and displays the user sign up. It validates the data entered into the
     *  form and stores it in the database table User.
     *  </p>
     *
     *  @author Michael
     *  @author Astrid
     *  @version 1.0
     *  @since 05.03.2020
     */

public class SignUpActivity extends AppCompatActivity {

    public static final String KEY = "UserLoggedIn";
    private EditText editUserName, editEmailAddress, editPassword, editConfirmPassword, editLoginHint;
    private String userName, emailAddress, editPasswordString, editConfirmPasswordString, editLoginHintString;
    private SpiceDatabase mySpiceRackDb;
    private User user;
    private UserDao myUserDao;
    private Button btnSignUp;
    private Navigation nav;

    /**
     *  <p>
     *  Implements the OnClickListener. If a button is clicked, the appropriate method is called.
     *  </p>
     */
    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnSignUp) {
                signUp();
            }
        }
    };

    /**
     *  <p>
     *  onCreate() initializes the SignUpActivity. It sets the layout, OnClickListeners, navigation and instantiates the
     *  database.
     *  </p>
     */
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

        nav = new Navigation(this);
    }

    /**
     *  <p>
     *  isEmailValid() checks if the characters entered into the editEmailAddress field
     *  are a valid email address.
     *  </p>
     */
    private boolean isEmailValid(CharSequence email) {
        emailAddress = editEmailAddress.getText().toString();
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    /**
     *  <p>
     *  checkIfExists() check if the email address entered into the editEmailAddress field
     *  already exists in the User table.
     *  </p>
     */
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

    /**
     *  <p>
     *  signUp() is called to add the data entered into the form to the user table. It performs the checks listed below
     *  and displays error messages in a toast if needed:
     *  <li>
     *      <ul>Ensures that password and confirm password are equal</ul>
     *      <ul>Ensures that all fields in the form contain data</ul>
     *      <ul>Verifies that the email address is valid</ul>
     *      <ul>Verifies that username and hint contain minimum 3 characters</ul>
     *      <ul>Verifies that the password contains minimum 6 characters</ul>
     *      <ul>Verifies that the user does not already exist in the database</ul>
     *  </li>
     *  </p>
     *  <p>
     *      After performing the checks above and no errors occur:
     *  <li>
     *      <ul>The data entered into the form is stored as new User object</ul>
     *      <ul>The screen continues to the next page, the homePage</ul>
     *      <ul>The email address entered is stored into Shared Preferences. This is needed when reopening the app
     *      in order to log in a user automatically.</ul>
     *  </li>
     *  </p>
     */
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

            nav.homePage();

            SharedPreferences prefPutU = getSharedPreferences("User", Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditorU = prefPutU.edit();
            prefEditorU.putString(KEY, emailAddress);
            prefEditorU.commit();
        }
    }
}