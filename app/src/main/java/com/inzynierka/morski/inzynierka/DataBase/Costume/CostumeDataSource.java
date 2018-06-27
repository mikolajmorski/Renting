package com.inzynierka.morski.inzynierka.DataBase.Costume;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.inzynierka.morski.inzynierka.DataBase.MySQLiteHelper;
import com.inzynierka.morski.inzynierka.Exeptions.NoCostumeInDataBaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mors on 2015-11-07.
 */
public class CostumeDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COSTUME_ID, MySQLiteHelper.COSTUME_NAME, MySQLiteHelper.COSTUME_LABEL };

    public CostumeDataSource (Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //Funkcja tworzenia kostiumu
    public Costume createCostume(String name, String label){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COSTUME_NAME, name);
        values.put(MySQLiteHelper.COSTUME_LABEL, label);
        long insertID = database.insert (MySQLiteHelper.TABLE_COSTUMES, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COSTUMES, allColumns,
                        MySQLiteHelper.COSTUME_ID + " = " + insertID, null,
                        null, null, null);
        cursor.moveToFirst();
        Costume newCostume = cursorToCostume(cursor);
        cursor.close();
        return newCostume;
    }

    public void deleteCostume (Costume costume){
        long id = costume.getId();
        database.delete(MySQLiteHelper.TABLE_COSTUMES, MySQLiteHelper.COSTUME_ID
                + " =  " + id, null);
    }

    public List<Costume> getAllCostumes() {
        List<Costume> comments = new ArrayList<Costume>();

        Cursor cursor = database.query (MySQLiteHelper.TABLE_COSTUMES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Costume comment = cursorToCostume(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    public Costume getCostume(String label) throws NoCostumeInDataBaseException {
        Costume costume = null;
        Cursor cursor = database.rawQuery("SELECT * FROM COSTUMES WHERE label = ?", new String[]{label});
        cursor.moveToFirst();
        if(cursor.isAfterLast())
            throw new NoCostumeInDataBaseException(label);

        costume = cursorToCostume(cursor);
        cursor.close();
        return costume;
    }

    public Costume getCostume(Long id) throws NoCostumeInDataBaseException{
        Costume costume = null;
        Cursor cursor = database.rawQuery("SELECT * FROM COSTUMES WHERE id = ?", new String[]{Long.toString(id)} );
        cursor.moveToFirst();
        if(cursor.isAfterLast()){
            throw new NoCostumeInDataBaseException(Long.toString(id));
        }
        costume = cursorToCostume(cursor);
        cursor.close();

        return costume;
    }

    public void updateCostume(Map<String, String> costumeData, long id){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COSTUME_LABEL, costumeData.get("label"));
        values.put(MySQLiteHelper.COSTUME_NAME, costumeData.get("name"));
        database.update(MySQLiteHelper.TABLE_COSTUMES, values, "id = ?", new String[]{Long.toString(id)});
    }



    private Costume cursorToCostume (Cursor cursor){
        Costume costume = new Costume();
        costume.setId(cursor.getLong(0));
        costume.setName(cursor.getString(1));
        costume.setLabel(cursor.getString(2));
        return costume;
    }
}
