package SpiceRack.Application.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(ShoppingItem shoppingItem);

    @Delete
    void deleteItem(ShoppingItem shoppingItem);

    @Update (onConflict = OnConflictStrategy.REPLACE)
    void upDate(ShoppingItem shoppingItem);

    @Query("SELECT * from shopping_list")
    List<ShoppingItem> getAllShoppingItems();

}
