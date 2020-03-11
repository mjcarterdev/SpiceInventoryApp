package SpiceRack.Application.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
/**
 *<p>
 *      SpiceDao is a data access object used by Room and includes methods that offer abstract access
 *      to the Spice Inventory table within Spice Database.
 *</p>
 *
 * @author Michael
 * @author Astrid
 * @version 1.0
 * @since 05.03.2020
 */
@Dao
public interface SpiceDao {
    /**
     *<p>
     *     insertSpice() is used to add a new Spice object to the spice inventory. If there is a
     *     conflict then the insert is ignored.
     *</p>
     * @param spice an object from the Spice Inventory.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSpice(Spice spice);

    /**
     * <p>
     *      deleteSpice() removes the object passed as a parameter from the spice inventory table.
     * </p>
     * @param spice an object from the Spice Inventory.
     */
    @Delete
    void deleteSpice(Spice spice);

    /**
     *<p>
     *     upDate() will replace the Spice passed as a parameter with the new Spice.
     *</p>
     * @param spice an object from the Spice Inventory.
     */
    @Update (onConflict = OnConflictStrategy.REPLACE)
    void upDate(Spice spice);

    /**
     * <p>
     *     Selects all the spices within the spice inventory table and creates a list of Spice
     *     objects.
     * </p>
     * @return a list of all the Spice objects within the spice inventory table.
     */
    @Query("SELECT * from spiceInventory")
    List<Spice> getAllSpices();

    /**
     * <p>
     *     Selects all the spices within the spice inventory table with the searched for spice name
     *     and creates a list of Spice objects.
     * </p>
     * @param name a string of a spice name to be searched for within the spice inventory table.
     * @return a list of Spice objects with the same searched for spice name value.
     */
    @Query("SELECT * FROM spiceInventory WHERE spiceName = :name")
    List<Spice> getSpiceByName(String name);

    /**
     * <p>
     *     Selects all the spices within the spice inventory table with the searched for barcode
     *     and creates a list of Spice objects.
     * </p>
     * @param barcode a string of a barcode to be searched for within the spice inventory table.
     * @return a list of Spice objects with the same searched for barcode value.
     */
    @Query("SELECT * FROM spiceInventory WHERE barcode = :barcode LIMIT 1")
    Spice getSpiceByBarcode(String barcode);

    /**
     * <p>
     *     Selects all the spices within the spice inventory table with the searched for stock
     *     and creates a list of Spice objects.
     * </p>
     * @param stock a number for stock to be searched for within the spice inventory table.
     * @return a list of Spice objects with the same searched for stock value.
     */
    @Query("SELECT * FROM spiceInventory WHERE stock = :stock")
    List<Spice> getSpiceByStock(int stock);
}