package SpiceRack.Application.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "spiceInventory", indices ={@Index(value = {"barcode"},
        unique = true)})
public class Spice implements Comparable<Spice>{


    @PrimaryKey (autoGenerate = true)
    private int spiceID;
    @NonNull
    private String barcode;
    private String spiceName;
    private int stock;
    private String containerType;
    private String brand;


    public Spice(@NonNull String barcode, String spiceName, int stock, String containerType, String brand){

        this.barcode = barcode;
        this.spiceName = spiceName;
        this.stock = stock;
        this.containerType = containerType;
        this.brand = brand;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @NonNull
    public String getBarcode(){
        return this.barcode;
    }

    public int getStock() {
        return this.stock;
    }

     public String getSpiceName() {
        return spiceName;
    }

    public String getInfo(int stock){
        return "you have " + stock + " units of " + this.spiceName + " in your inventory.";
    }

    public String getContainerType() {
        return containerType;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public int compareTo(Spice o) {
        return this.getSpiceName().compareTo(o.spiceName);
    }

    public int getSpiceID() {
        return spiceID;
    }

    void setSpiceID(int spiceID) {
        this.spiceID = spiceID;
    }

}
