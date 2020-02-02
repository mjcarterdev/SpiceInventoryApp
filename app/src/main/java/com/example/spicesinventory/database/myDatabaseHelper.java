package com.example.spicesinventory.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class myDatabaseHelper extends SQLiteOpenHelper {
    private static final  String dbNAME = "SpiceInventoryDB.db";
    static final  String TABLE_NAME = "Spice_Inventory";
    private static final  int DATABASE_VERSION = 1;
    static final  String UID ="_id";
    static final  String BARCODE_NUMBER = "Barcode";
    static final  String SPICE_NAME = "Name";
    static final  String STOCK_NUMBER = "STOCK";
    private static final  String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+ "("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+BARCODE_NUMBER+" INTEGER UNIQUE,"+ SPICE_NAME+" TEXT ,"+ STOCK_NUMBER+" INTEGER)";
    private static final  String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
    private Context context;

    myDatabaseHelper(Context context) {
        super(context, dbNAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_TABLE);
        } catch (Exception e){
            Toast.makeText(this.context, "Error: "+ e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            Toast.makeText(this.context, "OnUpgrade", Toast.LENGTH_SHORT).show();
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch(Exception e){
            Toast.makeText(this.context, ""+e, Toast.LENGTH_SHORT).show();
        }
    }
}