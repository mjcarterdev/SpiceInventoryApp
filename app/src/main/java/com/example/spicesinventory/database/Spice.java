package com.example.spicesinventory.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "spice_inventory")
public class Spice {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "Barcode")
    private String barcodeID;

    @ColumnInfo(name = "Spice_name")
    private String spice_name;

    @ColumnInfo(name = "Stock")
    private String stock;

    public Spice(@NonNull String barcodeID, String spice_name, String stock){

        this.barcodeID = barcodeID;
        this.spice_name = spice_name;
        this.stock = stock;
    }

    public void setBarcodeID(@NonNull String barcodeID) {
        this.barcodeID = barcodeID;
    }

    public void setSpiceName(String spice_name) {
        this.spice_name = spice_name;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
    @NonNull
    String getBarcodeID(){
        return this.barcodeID;
    }

    String getStock() {
        return this.stock;
    }

    String getSpice_name() {
        return spice_name;
    }
}
