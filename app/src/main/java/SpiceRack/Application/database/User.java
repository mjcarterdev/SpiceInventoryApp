package SpiceRack.Application.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 *<p>
 *     User() is a class to define the items stored in the User table and the values
 *     each object should store. Entity annotation is used to define that data is stored in the
 *     User table. UserID has been implemented as primary key for the table, it is automatically generated.
 *</p>
 * @author Michael
 * @author Astrid
 * @version 1.0
 * @since 05.03.2020
 */

@Entity(tableName = "users")
public class User {

    /**
     *<p>
     *     UserID, emailAddress, username, password, loginHint are the column names of the database table.
     *</p>
     */
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userID")
    private int userID;

    @NonNull
    @ColumnInfo(name = "email_address")
    private String emailAddress;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "loginHint")
    private String loginHint;

    /**
     *<p>
     *     User() constructor to create a new User object.
     *</p>
     * @param username value of the username.
     * @param emailAddress value of the email address.
     * @param password value of the password.
     * @param loginHint value of the login hint.
     */
    public User(String username, String emailAddress, String password, String loginHint) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.loginHint = loginHint;
    }

    /**
     *<p>
     *     Setters and getters for the User object.
     *</p>
     */
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginHint() {
        return loginHint;
    }

    public void setLoginHint(String loginHint) {
        this.loginHint = loginHint;
    }
}