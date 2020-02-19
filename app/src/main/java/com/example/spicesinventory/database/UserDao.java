package com.example.spicesinventory.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void upDate(User user);

    @Query("SELECT * FROM users WHERE password = :password")
    User getUserByPw(String password);

    @Query("SELECT * FROM users WHERE email_address = :email_address")
    User getUserByEmail(String email_address);

    @Query("SELECT * from users")
    List<User> getAllUsers();

}
