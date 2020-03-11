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
 *  LoginActivity class creates and displays the user log in. It either presents the log in form or
 *  logs in a user automatically.
 *  </p>
 *
 *  @author Michael
 *  @author Astrid
 *  @version 1.0
 *  @since 05.03.2020
 */

public class LoginActivity extends AppCompatActivity {

    public static final String KEY = "UserLoggedIn";
    private EditText editEmailAddress, editPassword;
    private String emailID, emailAddress, editPasswordString, pw;
    private SpiceDatabase mySpiceRackDb;
    private UserDao myUserDao;
    private Button btnLogIn, btnShowHint;
    private User tempEmail, tempPw;
    private Navigation nav;

    /**
     *  <p>
     *  Implements the OnClickListener. If a button is clicked, the appropriate method is called.
     *  </p>
     */
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

    /**
     *  <p>
     *  onCreate() initializes the LogInActivity. It sets the layout, OnClickListeners, navigation and instantiates the
     *  database.
     *  </p>
     */
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

        nav = new Navigation(this);
    }

    /**
     *  <p>
     *  showHint() displays the loginHint for a user if an email address is entered into the editEmailAddress field.
     *  </p>
     */
    private void showHint(){
        emailAddress = editEmailAddress.getText().toString();
        tempEmail = myUserDao.getUserByEmail(emailAddress);

        if(tempEmail != null){
            Toast.makeText(LoginActivity.this, tempEmail.getLoginHint(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *  <p>
     *  login() is called to log a user into the app if editEmailAddress and editPassword are equal to the
     *  email address and password stored in the User database. Additionally, the user's email address is
     *  stored in SharedPreferences. This is needed to log the user in automatically next time the app is opened. The
     *  screen continues to the next page, the homePage. If email address or password don't conform to the data in the
     *  User database, an error message is displayed as toast.
     *  </p>
     */
    private void logIn() {
        emailAddress = editEmailAddress.getText().toString();
        editPasswordString = editPassword.getText().toString();

        tempEmail = myUserDao.getUserByEmail(emailAddress);
        tempPw = myUserDao.getUserByPw(editPasswordString);

        if(tempEmail != null)
            emailID = tempEmail.getEmailAddress();

        if(tempPw != null)
            pw = tempPw.getPassword();

        if (emailAddress.equals(emailID) && editPasswordString.equals(pw)){

            SharedPreferences prefPut = getSharedPreferences("User", Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putString(KEY, emailAddress);
            prefEditor.commit();

            nav.homePage();

        } else {
            Toast.makeText(LoginActivity.this, "Wrong user or password", Toast.LENGTH_SHORT).show();
        }
    }
}