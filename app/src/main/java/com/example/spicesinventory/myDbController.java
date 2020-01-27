package com.example.spicesinventory;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.spicesinventory.myDatabaseHelper;


public class myDbController extends myDatabaseHelper {
    private myDatabaseHelper dbHelper;


    public myDbController(Context context) {
        super(context);

        dbHelper = new myDatabaseHelper(context);
    }

    public long insertData(String barcode, String spiceName, int Stock) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues cell_info = new ContentValues();
        cell_info.put(BARCODE_NUMBER, barcode);
        cell_info.put(SPICE_NAME, spiceName);
        cell_info.put(STOCK_NUMBER, Stock);
        return database.insert(TABLE_NAME, null, cell_info);
    }

    public int delete(String toDelete) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] toFind = {toDelete};
        return database.delete(TABLE_NAME, BARCODE_NUMBER + " = ?", toFind);

    }

    /*public String findName(String barcode) {

    SQLiteDatabase database = dbHelper.getWritableDatabase();
    String[] searchCriteria = {"Where =?" + barcode};
    String[] columns = {dbHelper.UID, dbHelper.BARCODE_NUMBER, dbHelper.SPICE_NAME, dbHelper.STOCK_NUMBER};
    Cursor cursor = database.query(dbHelper.TABLE_NAME, columns, null, searchCriteria, null, null, null);
    StringBuilder buffer = new StringBuilder();
        while(cursor.moveToNext())
    {
        String rowName = cursor.getString(cursor.getColumnIndex(dbHelper.SPICE_NAME));
        buffer.append(rowName).append(" \n");
    }
        cursor.close();
        return buffer.toString();
    }*/

    public String getBarcode(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] columns = {UID, BARCODE_NUMBER, SPICE_NAME, STOCK_NUMBER};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
        StringBuilder buffer = new StringBuilder();
        while(cursor.moveToNext()){
            String barcode = cursor.getString(cursor.getColumnIndex(BARCODE_NUMBER));
            buffer.append(barcode).append(" \n");
        }
        cursor.close();
        return buffer.toString();
    }

    public String getName(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] columns = {UID, BARCODE_NUMBER, SPICE_NAME, STOCK_NUMBER};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
        StringBuilder buffer = new StringBuilder();
        while(cursor.moveToNext()){
            String SpiceName = cursor.getString(cursor.getColumnIndex(SPICE_NAME));
            buffer.append(SpiceName).append(" \n");
        }
        cursor.close();
        return buffer.toString();

    }

    public String getStock(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] columns = {UID, BARCODE_NUMBER, SPICE_NAME, STOCK_NUMBER};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
        StringBuilder buffer = new StringBuilder();
        while(cursor.moveToNext()){
            int StockNumber = cursor.getInt(cursor.getColumnIndex(STOCK_NUMBER));
            buffer.append(StockNumber).append(" \n");
        }
        cursor.close();
        return buffer.toString();
    }

     public int updateStock(String barcode, int newStock){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STOCK_NUMBER, newStock);
        String[] whereArgs= {barcode};
         return database.update(TABLE_NAME,contentValues, BARCODE_NUMBER +" = ?",whereArgs );
    }






}
