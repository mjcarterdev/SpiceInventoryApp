package SpiceRack.Application.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
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
    @NonNull
    private String amount;
    private boolean strikeThrough;

    public ShoppingItem(@NonNull String itemName,@NonNull String amount) {
        this.itemName = itemName;
        this.amount = amount;
        this.strikeThrough = false;
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

    @NotNull
    String getAmount() {
        return amount;
    }

    void setAmount(@NonNull  String amount) {
        this.amount = amount;
    }

    boolean isStrikeThrough() {
        return strikeThrough;
    }

    void setStrikeThrough(boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
    }
}
