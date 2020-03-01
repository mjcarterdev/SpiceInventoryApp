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
    private boolean isSpice;
    private int viewType;
    private String containerType;
    private String brand;

    public ShoppingItem(@NonNull String itemName, int amount, String containerType, String brand, int viewType, boolean isSpice){
        this.itemName = itemName;
        this.amount = amount;
        this.isSpice = isSpice;
        this.containerType = containerType;
        this.brand = brand;
        this.viewType = viewType;
    }

    public int getShoppingID() {
        return shoppingID;
    }

    public void setShoppingID(int shoppingID) {
        this.shoppingID = shoppingID;
    }

    @NotNull
    public String getItemName() {
        return itemName;
    }

    public void setItemName(@NotNull String item_name) {
        this.itemName = item_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getContainerType() {
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

    public boolean isSpice() {
        return isSpice;
    }

    public void setSpice(boolean spice) {
        isSpice = spice;
    }
}
