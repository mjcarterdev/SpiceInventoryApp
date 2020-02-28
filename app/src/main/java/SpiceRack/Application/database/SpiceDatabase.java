package SpiceRack.Application.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Spice.class, ShoppingItem.class,  User.class}, version = 1, exportSchema = false)
public abstract class SpiceDatabase extends RoomDatabase {

    public abstract SpiceDao getSpiceDao();
    public abstract ShoppingDao getShoppingDao();
    public abstract UserDao getUserDao();


    private static volatile SpiceDatabase INSTANCE;

    public static SpiceDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (SpiceDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SpiceDatabase.class, "SpiceRack.db").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}




