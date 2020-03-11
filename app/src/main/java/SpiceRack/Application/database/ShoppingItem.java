package SpiceRack.Application.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

/**
 *<p>
 *     ShoppingItem() is a class to define the items stored in the shoppingList table and the values
 *     each object should store. Entity annotation is used to define the table the objects are to be
 *     stored in and that itemNames should be unique to prevent duplication.
 *</p>
 * @author Michael
 * @author Astrid
 * @version 1.0
 * @since 05.03.2020
 */
@Entity(tableName = "shoppingList", indices ={@Index(value = {"itemName"},
        unique = true)})
public class ShoppingItem {

    @PrimaryKey(autoGenerate = true)
    private int shoppingID;
    @NonNull
    private String itemName;
    private int amount;
    private boolean isSpice;
    private int viewType;
    private String containerType;
    private String brand;

    /**
     * <p>
     *     ShoppingItem() constructor used to create a new shopping item object.
     * </p>
     * @param itemName name of the item.
     * @param amount how many of the item.
     * @param containerType what is the container type of the item.
     * @param brand what is the brand of the item.
     * @param viewType what type of view is need upon display.
     * @param isSpice is the object a spice from the inventory or a manual input item.
     */
    public ShoppingItem(@NonNull String itemName, int amount, String containerType, String brand, int viewType, boolean isSpice){
        this.itemName = itemName;
        this.amount = amount;
        this.isSpice = isSpice;
        this.containerType = containerType;
        this.brand = brand;
        this.viewType = viewType;
    }

    /**
     * <p>
     *     gets the shopping ID of the object.
     * </p>
     * @return shopping ID value.
     */
    int getShoppingID() {
        return shoppingID;
    }

    /**
     * <p>
     *     sets the shopping ID of the object.
     * </p>
     * @param shoppingID new value of the shoppingID to be set.
     */
    void setShoppingID(int shoppingID) {
        this.shoppingID = shoppingID;
    }

    /**
     * <p>
     *     gets item name of the object.
     * </p>
     * @return item name value.
     */
    @NotNull
    public String getItemName() {
        return itemName;
    }

    /**
     * <p>
     *     set new value to item name.
     * </p>
     * @param itemName the new value for item name
     */
    public void setItemName(@NotNull String itemName) {
        this.itemName = itemName;
    }

    /**
     * <p>
     *     get amount for object
     * </p>
     * @return the amount value for the object
     */
    int getAmount() {
        return amount;
    }

    /**
     * <p>
     *     sets new value to amount in the object.
     * </p>
     * @param amount new amount value for the object.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * <p>
     *     gets the viewType value
     * </p>
     * @return viewType value.
     */
    public int getViewType() {
        return viewType;
    }

    /**
     * <p>
     *     sets the viewType value
     * </p>
     * @param viewType new viewType value for the object.
     */
    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    /**
     * <p>
     *     get teh container type for the object
     * </p>
     * @return the value of container type.
     */
    String getContainerType() {
        return containerType;
    }

    /**
     * <p>
     *     gets the brand of the object
     * </p>
     * @return objects brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * <p>
     *     sets the objects brand to the new value.
     * </p>
     * @param brand new brand value.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * <p>
     *     gets the value of isSpice.
     * </p>
     * @return is it a spice true or false
     */
    public boolean isSpice() {
        return isSpice;
    }

    /**
     * <p>
     *     sets isSpice to true or false.
     * </p>
     * @param spice new value of isSpice, true or false.
     */
    public void setSpice(boolean spice) {
        isSpice = spice;
    }
}