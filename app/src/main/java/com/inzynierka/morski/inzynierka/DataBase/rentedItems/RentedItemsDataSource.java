package com.inzynierka.morski.inzynierka.DataBase.rentedItems;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.inzynierka.morski.inzynierka.DataBase.MySQLiteHelper;
import com.inzynierka.morski.inzynierka.DataBase.rent.Rent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asdf on 2016-01-04.
 */
public class RentedItemsDataSource {


    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.RENTED_ITEMS_ID,
            MySQLiteHelper.RENTED_ITEMS_RENT_ID,
            MySQLiteHelper.RENTED_ITEMS_COSTUME_ID,
            MySQLiteHelper.RENTED_ITEMS_POSITION};

    public RentedItemsDataSource (Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public RentedItems createRentedItem (long rentId, long costumeId, int position){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.RENTED_ITEMS_RENT_ID, rentId);
        values.put(MySQLiteHelper.RENTED_ITEMS_COSTUME_ID, costumeId);
        values.put(MySQLiteHelper.RENTED_ITEMS_POSITION, position);
        long insertID = database.insert (MySQLiteHelper.TABLE_RENTED_ITEMS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_RENTED_ITEMS, allColumns,
                MySQLiteHelper.RENTED_ITEMS_ID + " = " + insertID, null,
                null, null, null);
        cursor.moveToFirst();
        RentedItems newRentedItem = cursorToRentedItem(cursor);
        cursor.close();
        return newRentedItem;
    }

    public void deleteRentedItem (RentedItems rentedItem){
        long id = rentedItem.getId();
        database.delete(MySQLiteHelper.TABLE_RENTED_ITEMS, MySQLiteHelper.RENTED_ITEMS_ID
                + " =  " + id, null);
    }

    public List<RentedItems> getAllRentedItems() {
        List<RentedItems> rentedItems = new ArrayList<RentedItems>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_RENTED_ITEMS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            RentedItems rentedItem = cursorToRentedItem(cursor);
            rentedItems.add(rentedItem);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return rentedItems;
    }

    public RentedItems getRentedItem(Long costumeId){
        RentedItems rentedItem = null;
        Cursor cursor = database.rawQuery("SELECT * FROM RENTED_ITEMS WHERE costume_id = ?", new String[]{Long.toString(costumeId)});
        if(cursor.isAfterLast())
            return null;

        cursor.moveToFirst();
        rentedItem = cursorToRentedItem(cursor);
        cursor.close();

        return rentedItem;
    }

    public void getRentedItemsOfOneCostume(Long costumeId){
        List<RentedItems> rentedItems = new ArrayList<RentedItems>();

        Cursor cursor = database.rawQuery("SELECT * FROM RENTED_ITEMS WHERE costume_id = ?", new String[]{Long.toString(costumeId)});

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            RentedItems rentedItem = cursorToRentedItem(cursor);
            rentedItems.add(rentedItem);
            System.out.println("Kostium id: " + rentedItem.getCostumeId() + " jego pozycja to: " + rentedItem.getPosition());
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
    }

    public RentedItems getActivRentedItem(Long costumeId){
        RentedItems rentedItem = null;
        Cursor cursor = database.rawQuery("SELECT * FROM RENTED_ITEMS WHERE costume_id = ?", new String[]{Long.toString(costumeId)});

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if(cursorToRentedItem(cursor).getPosition()==1){
                rentedItem = cursorToRentedItem(cursor);
                break;
            }
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return rentedItem;
    }

    public ArrayList<RentedItems> getRentedItemsOfCostume(Long costumeId){
        ArrayList<RentedItems> rentedItems = new ArrayList<RentedItems>();
        Cursor cursor = database.rawQuery("SELECT * FROM RENTED_ITEMS WHERE costume_id = ?", new String[]{Long.toString(costumeId)});

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            rentedItems.add(cursorToRentedItem(cursor));
            cursor.moveToNext();
        }
        return rentedItems;
    }

    public void updateRentedItem(int position, Long rentedItemId){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.RENTED_ITEMS_POSITION, String.valueOf(position));
        database.update(MySQLiteHelper.TABLE_RENTED_ITEMS, values, "id = ?", new String[]{Long.toString(rentedItemId)});
    }

    private RentedItems cursorToRentedItem (Cursor cursor){
        RentedItems rentedItem = new RentedItems();
        rentedItem.setId(cursor.getLong(0));
        rentedItem.setRentId(cursor.getLong(1));
        rentedItem.setCostumeId(cursor.getLong(2));
        rentedItem.setPosition(cursor.getInt(3));
        return rentedItem;
    }
}
