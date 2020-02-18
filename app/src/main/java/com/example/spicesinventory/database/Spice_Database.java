package com.example.spicesinventory.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Spice.class}, version = 1, exportSchema = false)
public abstract class Spice_Database extends RoomDatabase {

    public abstract SpiceDao getSpiceDao();


    private static volatile Spice_Database INSTANCE;

    public static Spice_Database getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (Spice_Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Spice_Database.class, "SpiceRack.db").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}




