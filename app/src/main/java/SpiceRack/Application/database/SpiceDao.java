package SpiceRack.Application.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SpiceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSpice(Spice spice);

    @Delete
    void deleteSpice(Spice spice);

    @Update (onConflict = OnConflictStrategy.REPLACE)
    void upDate(Spice spice);

    @Query("SELECT * from spiceInventory")
    List<Spice> getAllSpices();

    @Query("SELECT * FROM spiceInventory WHERE spiceName = :name")
    List<Spice> getSpiceByName(String name);

    @Query("SELECT * FROM spiceInventory WHERE barcode = :barcode LIMIT 1")
    Spice getSpiceByBarcode(String barcode);

    @Query("SELECT * FROM spiceInventory WHERE stock = :stock")
    List<Spice> getSpiceByStock(String stock);
}
