package SpiceRack.Application.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "shoppingList", indices ={@Index(value = {"itemName"},
        unique = true)})
public class ShoppingItem {

    @PrimaryKey(autoGenerate = true)
    private int shoppingID;
    @NonNull
    private String itemName;
    private int amount;
    private boolean strikeThrough;
    private int viewType;
    private String containerType;
    private String brand;

    /*public ShoppingItem(@NonNull String itemName, int amount) {
        this.itemName = itemName;
        this.amount = amount;
        this.strikeThrough = false;
        this.viewType = 0;
    }*/

    public ShoppingItem(@NonNull String itemName, int amount, String containerType, String brand, int viewType){
        this.itemName = itemName;
        this.amount = amount;
        this.strikeThrough = false;
        this.containerType = containerType;
        this.brand = brand;
        this.viewType = viewType;
    }

    int getShoppingID() {
        return shoppingID;
    }

    void setShoppingID(int shoppingID) {
        this.shoppingID = shoppingID;
    }

    @NotNull
    String getItemName() {
        return itemName;
    }

    void setItemName(@NotNull String item_name) {
        this.itemName = item_name;
    }


    int getAmount() {
        return amount;
    }

    void setAmount(int amount) {
        this.amount = amount;
    }

    boolean isStrikeThrough() {
        return strikeThrough;
    }

    void setStrikeThrough(boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
    }

    int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
