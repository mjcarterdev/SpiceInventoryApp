package SpiceRack.Application.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 *<p>
 *      UserDao is a data access object used by Room and includes methods that offer abstract access
 *      to the User table within the Spice Database.
 *</p>
 *
 * @author Michael
 * @author Astrid
 * @version 1.0
 * @since 05.03.2020
 */

@Dao
public interface UserDao {
    /**
     *<p>
     *     insertUser() is used to add a new User object to the User table. If there is a
     *     conflict, the insert is ignored. This is called upon Sign up.
     *</p>
     * @param user an object from the User table.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(User user);

    /**
     *<p>
     *     deleteUser() is used to remove a user object from the User table. This is called
     *     when deleting a user account.
     *</p>
     */
    @Delete
    void deleteUser(User user);

    /**
     *<p>
     *     upDate() is used to update a user object in the User table. This is called
     *     when editing the user profile.
     *</p>
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void upDate(User user);

    /**
     *<p>
     *     getUserByPw() is used to select stored user data by password. This is used to compare
     *     the stored password with the password entered upon login.
     *</p>
     */
    @Query("SELECT * FROM users WHERE password = :password")
    User getUserByPw(String password);

    /**
     *<p>
     *     getUserByEmail() is used to select stored user data by email address. This is used to load user data
     *     in the login activity.
     *</p>
     */
    @Query("SELECT * FROM users WHERE email_address = :email_address")
    User getUserByEmail(String email_address);
}