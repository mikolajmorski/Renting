package com.inzynierka.morski.inzynierka.DataBase.Client;

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
 * Created by Asdf on 2015-12-01.
 */
public class ClientDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.CLIENT_ID,
                                    MySQLiteHelper.CLIENT_NAME,
                                    MySQLiteHelper.CLIENT_LAST_NAME,
                                    MySQLiteHelper.CLIENT_ADDRESS,
                                    MySQLiteHelper.CLIENT_POSTAL_CODE,
                                    MySQLiteHelper.CLIENT_CITY,
                                    MySQLiteHelper.CLIENT_PHONE_NUMBER,
                                    MySQLiteHelper.CLIENT_MAIL};

    public ClientDataSource (Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public Client createClient(String name, String lastName, String address, long postalCode, String city, int phoneNumber, String mail){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.CLIENT_NAME, name);
        values.put(MySQLiteHelper.CLIENT_LAST_NAME, lastName);
        values.put(MySQLiteHelper.CLIENT_ADDRESS, address);
        values.put(MySQLiteHelper.CLIENT_POSTAL_CODE, postalCode);
        values.put(MySQLiteHelper.CLIENT_CITY, city);
        values.put(MySQLiteHelper.CLIENT_PHONE_NUMBER, phoneNumber);
        values.put(MySQLiteHelper.CLIENT_MAIL, mail);
        long insertID = database.insert (MySQLiteHelper.TABLE_CLIENT, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CLIENT, allColumns,
                MySQLiteHelper.CLIENT_ID + " = " + insertID, null,
                null, null, null);
        cursor.moveToFirst();
        Client newClient = cursorToClient(cursor);
        cursor.close();
        return newClient;
    }

    public void deleteClient (Client client){
        long id = client.getId();
        database.delete(MySQLiteHelper.TABLE_CLIENT, MySQLiteHelper.CLIENT_ID
                + " =  " + id, null);
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();

        Cursor cursor = database.query (MySQLiteHelper.TABLE_CLIENT,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Client client = cursorToClient(cursor);
            clients.add(client);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return clients;
    }

    //FIXME Metoda działa ale beznadziejnie bo zawsze przeszukuje wszystkich klientow
    public Client getClient(long id){
        for(Client klient : getAllClients()){
            if(klient.getId()==id){
                return klient;
            }
        }
        return null;
    }

    public void updateClient(Map<String, String> personData, long id){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.CLIENT_NAME, personData.get("name"));
        values.put(MySQLiteHelper.CLIENT_LAST_NAME, personData.get("lastName"));
        values.put(MySQLiteHelper.CLIENT_ADDRESS, personData.get("address"));
        values.put(MySQLiteHelper.CLIENT_POSTAL_CODE, personData.get("postalCode"));
        values.put(MySQLiteHelper.CLIENT_CITY, personData.get("city"));
        values.put(MySQLiteHelper.CLIENT_PHONE_NUMBER, personData.get("phone"));
        values.put(MySQLiteHelper.CLIENT_MAIL, personData.get("mail"));
        database.update(MySQLiteHelper.TABLE_CLIENT, values, "id = ?", new String[]{Long.toString(id)});
    }

    private Client cursorToClient (Cursor cursor){
        Client client = new Client();
        client.setId(cursor.getLong(0));
        client.setName(cursor.getString(1));
        client.setLastName(cursor.getString(2));
        client.setAddress(cursor.getString(3));
        client.setPostalCode(cursor.getInt(4));
        client.setCity(cursor.getString(5));
        client.setPhoneNumber(cursor.getInt(6));
        client.setMail(cursor.getString(7));
        return client;
    }
}
