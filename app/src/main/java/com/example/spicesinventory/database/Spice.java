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

    @ColumnInfo(name="contain_type")
    private String containerType;

    @ColumnInfo(name="brand")
    private String brand;

    @ColumnInfo(name="strikeThrough")
    private boolean strikeThrough;


    public Spice(@NonNull String barcodeID, String spice_name, String stock, String containerType, String brand){

        this.barcodeID = barcodeID;
        this.spice_name = spice_name;
        this.stock = stock;
        this.containerType = containerType;
        this.brand = brand;
        this.strikeThrough = false;
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
    public String getBarcodeID(){
        return this.barcodeID;

    }

    public String getStock() {
        return this.stock;
    }

     public String getSpice_name() {
        return spice_name;
    }

    public String getInfo(){
        return "you have " + this.stock + " units of " + this.spice_name + " in your inventory.";
    }

    public void setSpice_name(String spice_name) {
        this.spice_name = spice_name;
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

    public boolean isStrikeThrough() {
        return strikeThrough;
    }

    public void setStrikeThrough(boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
    }
}
