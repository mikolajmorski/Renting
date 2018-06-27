package com.inzynierka.morski.inzynierka.DataBase.rent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.inzynierka.morski.inzynierka.DataBase.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Asdf on 2016-01-04.
 */
public class RentDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.RENT_ID,
            MySQLiteHelper.RENT_CLIENT_ID,
            MySQLiteHelper.RENT_DATE_RENT,
            MySQLiteHelper.RENT_DATE_RETURN,
            MySQLiteHelper.RENT_DATE_TRUE_RETURN,
            MySQLiteHelper.RENT_PRICE,
            MySQLiteHelper.RENT_STATUS};

    public RentDataSource (Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public Rent createRent(long clientId, String dateRent, String dateReturn, String dateTrueReturn, int price, String status){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.RENT_CLIENT_ID, clientId);
        values.put(MySQLiteHelper.RENT_DATE_RENT, dateRent);
        values.put(MySQLiteHelper.RENT_DATE_RETURN, dateReturn);
        values.put(MySQLiteHelper.RENT_DATE_TRUE_RETURN, dateTrueReturn);
        values.put(MySQLiteHelper.RENT_PRICE, price);
        values.put(MySQLiteHelper.RENT_STATUS, status);
        long insertID = database.insert (MySQLiteHelper.TABLE_RENT, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_RENT, allColumns,
                MySQLiteHelper.RENT_ID + " = " + insertID, null,
                null, null, null);
        cursor.moveToFirst();
        Rent newRent = cursorToRent(cursor);
        cursor.close();
        return newRent;
    }

    public void deleteRent (Rent rent){
        long id = rent.getId();
        database.delete(MySQLiteHelper.TABLE_RENT, MySQLiteHelper.RENT_ID
                + " =  " + id, null);
    }

    public List<Rent> getAllRents() {
        List<Rent> rents = new ArrayList<Rent>();

        Cursor cursor = database.query (MySQLiteHelper.TABLE_RENT,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Rent rent = cursorToRent(cursor);
            rents.add(rent);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return rents;
    }

    public Rent getRent(Long id){
        for(Rent iterator : getAllRents()){
            if(iterator.getId()==id){
                return iterator;
            }
        }
        return null;
    }

    public void updateRent(Map<String, String> rentData, long id){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.RENT_CLIENT_ID, rentData.get("clientId"));
        values.put(MySQLiteHelper.RENT_DATE_RENT, rentData.get("dateRent"));
        values.put(MySQLiteHelper.RENT_DATE_RETURN, rentData.get("dateReturn"));
        values.put(MySQLiteHelper.RENT_DATE_TRUE_RETURN, rentData.get("dateTrueReturn"));
        values.put(MySQLiteHelper.RENT_PRICE, rentData.get("price"));
        values.put(MySQLiteHelper.RENT_STATUS, rentData.get("status"));
        database.update(MySQLiteHelper.TABLE_RENT, values, "id = ?", new String[]{Long.toString(id)});
    }

    private Rent cursorToRent (Cursor cursor){
        Rent rent = new Rent();
        rent.setId(cursor.getLong(0));
        rent.setClientId(cursor.getLong(1));
        rent.setDateRent(cursor.getString(2));
        rent.setDateReturn(cursor.getString(3));
        rent.setDateTrueReturn(cursor.getString(4));
        rent.setPrice(cursor.getInt(5));
        rent.setStatus(cursor.getString(6));
        return rent;
    }
}
