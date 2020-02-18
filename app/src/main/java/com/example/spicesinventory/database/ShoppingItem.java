package com.example.spicesinventory.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_list")
public class ShoppingItem {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int shoppingID;

    @ColumnInfo(name = "item_name")
    private String item_name;

    @ColumnInfo(name = "amount")
    private String amount;

    @ColumnInfo(name = "strikethrough")
    private boolean strikeThrough;

    /*public ShoppingItem(@NonNull int shoppingID, String item_name, String amount, boolean strikeThrough) {
        this.shoppingID = shoppingID;
        this.item_name = item_name;
        this.amount = amount;
        this.strikeThrough = strikeThrough;
    }*/

    public ShoppingItem(String item_name, String amount) {
        this.item_name = item_name;
        this.amount = amount;
        this.strikeThrough = false;
    }

    @NonNull
    public int getShoppingID() {
        return shoppingID;
    }

    public void setShoppingID(@NonNull int shoppingID) {
        this.shoppingID = shoppingID;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isStrikeThrough() {
        return strikeThrough;
    }

    public void setStrikeThrough(boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
    }
}
