package SpiceRack.Application.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 *<p>
 *     Spice() is a class to define the items stored in the spiceInventory table and the values
 *     each object should store. Entity annotation is used to define the table the objects are to be
 *     stored in and that barcode's should be unique to prevent duplication.
 *</p>
 * <p>
 *     Implements Comparable interface that imposes natural ordering of the objects in the lists.
 *     The natural ordering is based on the objects spice name in alphabetical order.
 * </p>
 * @author Michael
 * @author Astrid
 * @version 1.0
 * @since 05.03.2020
 */

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
    private Boolean isSpice;

    /**
     *<p>
     *     Spice() constructor to create a new Spice object.
     *</p>
     * @param barcode value of the barcode.
     * @param spiceName value of the spice name.
     * @param stock value of the stock.
     * @param containerType value of the container type.
     * @param brand value of the brand.
     */

    public Spice(@NonNull String barcode, String spiceName, int stock, String containerType, String brand){

        this.barcode = barcode;
        this.spiceName = spiceName;
        this.stock = stock;
        this.containerType = containerType;
        this.brand = brand;
        this.isSpice = true;
    }

    /**
     *<p>
     *     gets the barcode of the object.
     *</p>
     * @return barcode value of the object.
     */

    @NonNull
    public String getBarcode(){
        return this.barcode;
    }

    /**
     * <p>
     *     gets the spice name of the object.
     * </p>
     * @return spice name value of the object.
     */

    public String getSpiceName() {
        return spiceName;
    }

    /**
     *<p>
     *      sets the spice name of the object.
     *</p>
     * @param spiceName new spice name value.
     */

    public void setSpiceName(String spiceName) {
        this.spiceName = spiceName;
    }

    /**
     *<p>
     *     gets the container type of the object.
     *</p>
     * @return container type value of the object.
     */

    public String getContainerType() {
        return containerType;
    }

    /**
     *<p>
     *     sets the container type of the object.
     *</p>
     * @param containerType new container type value.
     */

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    /**
     *<p>
     *     gets the stock of the object.
     *</p>
     * @return stock value of the object.
     */

    public int getStock() {
        return this.stock;
    }

    /**
     *<p>
     *     sets the stock value of the object.
     *</p>
     * @param stock new stock value.
     */

    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *<p>
     *     get the spice ID of the object.
     *</p>
     * @return the value of spice ID.
     */

    public int getSpiceID() {
        return spiceID;
    }

    /**
     *<p>
     *     sets the spice ID of the object.
     *</p>
     * @param spiceID new value for spice ID.
     */

    void setSpiceID(int spiceID) {
        this.spiceID = spiceID;
    }

    /**
     *<p>
     *     get the boolean value of isSpice.
     *</p>
     * @return value of isSpice.
     */

    public Boolean getSpice() {
        return isSpice;
    }

    /**
     *<p>
     *     set the boolean value of isSpice.
     *</p>
     * @param spice new boolean of isSpice.
     */

    public void setSpice(Boolean spice) {
        isSpice = spice;
    }

    /**
     *<p>
     *     get the brand of the object.
     *</p>
     * @return value of the brand.
     */

    public String getBrand() {
        return brand;
    }

    /**
     *<p>
     *     sets the brand of the object.
     *</p>
     * @param brand new value of brand.
     */

    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     *<p>
     *      Compares this spice object with the specified spice object for order. Returns a negative
     *      integer, zero, or a positive integer as this object is less than, equal to, or greater
     *      than the specified object.
     *</p>
     * @param o the object to be compared
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to,
     *         or greater than the specified object.
     */

    @Override
    public int compareTo(Spice o) {
        return this.getSpiceName().compareTo(o.spiceName);
    }

    /**
     *<p>
     *     gets the objects stock and spice name and fills in the blanks of the string to display
     *     the number of items of a given spice name exist in the inventory.
     *</p>
     * @param stock the amount of stock with the same spice name as this object
     * @return string stating the total stock of a spice with a specific name.
     */

    public String getInfo(int stock){
        return "you have " + stock + " units of " + this.spiceName + " in your inventory.";
    }

}
