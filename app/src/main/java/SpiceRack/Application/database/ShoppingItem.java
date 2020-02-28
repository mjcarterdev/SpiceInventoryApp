package SpiceRack.Application.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "shoppingList")
public class ShoppingItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int shoppingID;
    @NonNull
    @ColumnInfo(name = "item_name")
    private String item_name;
    @NonNull
    @ColumnInfo(name = "amount")
    private String amount;
    @ColumnInfo(name = "strikethrough")
    private boolean strikeThrough;

    public ShoppingItem(@NonNull String item_name,@NonNull String amount) {
        this.item_name = item_name;
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
    String getItem_name() {
        return item_name;
    }

    void setItem_name(@NotNull String item_name) {
        this.item_name = item_name;
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
