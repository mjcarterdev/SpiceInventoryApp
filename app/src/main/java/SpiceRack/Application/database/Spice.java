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
    private String stock;
    private String containerType;
    private String brand;
    private boolean strikeThrough;


    public Spice(@NonNull String barcode, String spiceName, String stock, String containerType, String brand){

        this.barcode = barcode;
        this.spiceName = spiceName;
        this.stock = stock;
        this.containerType = containerType;
        this.brand = brand;
        this.strikeThrough = false;
    }

    public void setBarcodeID(@NonNull String barcode) {
        this.barcode = barcode;
    }

    public void setSpiceName(String spice_name) {
        this.spiceName = spice_name;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    @NonNull
    public String getBarcode(){
        return this.barcode;
    }

    public String getStock() {
        return this.stock;
    }

     public String getSpiceName() {
        return spiceName;
    }

    public String getInfo(int stock){
        return "you have " + stock + " units of " + this.spiceName + " in your inventory.";
    }


    String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    boolean isStrikeThrough() {
        return strikeThrough;
    }

    void setStrikeThrough(boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
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
