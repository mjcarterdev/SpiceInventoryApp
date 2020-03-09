package SpiceRack.Application.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 *<p>
 *     SpiceDatabase is an abstract class that is used as a holder for a database. The class extends
 *     RoomDatabase which is used to manage the applications database. The @Database annotation
 *     specifies the entities that are used in the database. Each entity has its own table and specifies
 *     the data to be captured on each row.
 *</p>
 * <p>
 *     The database is managed using a singleton approach, that ensures only one instance of the
 *     database exists and is accessible globally. When the database is needed the method getInstance()
 *     is called and it will check if an INSTANCE already exists. If so, it will return that INSTANCE.
 *     If it does not exist then a synchronized block is used to create a new database with the specified
 *     parameters. The synchronized block prevents multiple threads running the code at the same time,
 *     stopping corruption of the data.
 * </p>
 * <p>
 *     Three abstractions are referenced each to a specific DAO linked to a unique table of data. The
 *     volatile variable of INSTANCE is to ensure that the database is saved to main memory and not CPU
 *     cache. This ensures that the data is as recent as possible if accessed from multiple threads.
 * </p>
 * @author Michael
 * @author Astrid
 * @version 1.0
 * @since 05.03.2020
 */
@Database(entities = {Spice.class, ShoppingItem.class,  User.class}, version = 1, exportSchema = false)
public abstract class SpiceDatabase extends RoomDatabase {

    public abstract SpiceDao getSpiceDao();
    public abstract ShoppingDao getShoppingDao();
    public abstract UserDao getUserDao();


    private static volatile SpiceDatabase INSTANCE;

    /**
     * <p>
     *     Creates or returns the single instance of the Spice Database.
     * </p>
     * @param context the context of the environment when called.
     * @return a single database INSTANCE.
     */
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




