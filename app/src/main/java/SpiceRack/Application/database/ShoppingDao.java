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
 *      ShoppingDao is a data access object used by Room and includes methods that offer abstract access
 *      to the ShoppingList table within Spice Database.
 *</p>
 *
 * @author Michael
 * @author Astrid
 * @version 1.0
 * @since 05.03.2020
 */
@Dao
public interface ShoppingDao {
    /**
     *<p>
     *     insertItem() is used to add a new shoppingItem object to the shopping list. If there is a
     *     conflict then the insert is ignored.
     *</p>
     * @param shoppingItem an object from the shoppingList.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(ShoppingItem shoppingItem);

    /**
     *<p>
     *     deleteItem() removes the object passed as a parameter from the shoppingList table.
     *</p>
     * @param shoppingItem an object from the shoppingList.
     */
    @Delete
    void deleteItem(ShoppingItem shoppingItem);

    /**
     *<p>
     *     upDate() will replace the shoppingItem passed as a parameter with the new shoppingItem.
     *</p>
     * @param shoppingItem an object from the shoppingList.
     */
    @Update (onConflict = OnConflictStrategy.REPLACE)
    void upDate(ShoppingItem shoppingItem);

    /**
     * <p>
     *     Selects all the items within the shoppingList table and creates a list of shoppingItem
     *     objects.
     * </p>
     * @return a list of all the shoppingItems from the table.
     */
    @Query("SELECT * from shoppingList")
    List<ShoppingItem> getAllShoppingItems();

}