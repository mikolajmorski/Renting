package com.inzynierka.morski.inzynierka.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Mors on 2015-11-07.
 * W tej klasie tworzona jest baza danych i wszystkie tabele
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_COSTUMES = "costumes";
    public static final String COSTUME_ID = "id";
    public static final String COSTUME_NAME = "name";
    public static final String COSTUME_LABEL = "label";

    public static final String TABLE_CLIENT = "client";
    public static final String CLIENT_ID = "id";
    public static final String CLIENT_NAME = "name";
    public static final String CLIENT_LAST_NAME = "last_name";
    public static final String CLIENT_ADDRESS = "address";
    public static final String CLIENT_POSTAL_CODE = "postal_code";
    public static final String CLIENT_CITY = "city";
    public static final String CLIENT_PHONE_NUMBER = "phone_number";
    public static final String CLIENT_MAIL = "mail";

    public static final String TABLE_RENT = "rent";
    public static final String RENT_ID = "id";
    public static final String RENT_CLIENT_ID = "client_id";
    public static final String RENT_DATE_RENT = "date_rent";
    public static final String RENT_DATE_RETURN = "date_return";
    public static final String RENT_DATE_TRUE_RETURN = "date_true_return";
    public static final String RENT_PRICE = "price";
    public static final String RENT_STATUS = "status";

    public static final String TABLE_RENTED_ITEMS = "rented_items";
    public static final String RENTED_ITEMS_ID = "id";
    public static final String RENTED_ITEMS_RENT_ID = "rent_id";
    public static final String RENTED_ITEMS_COSTUME_ID = "costume_id";
    public static final String RENTED_ITEMS_POSITION = "position";


    private static final String DATABASE_NAME = "database.id";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String CREATE_COSTUME_TABLE = "create table "
            + TABLE_COSTUMES + "("
            + COSTUME_ID + " integer primary key autoincrement, "
            + COSTUME_NAME + " text not null, "
            + COSTUME_LABEL + " text not null); ";


    private static final String CREATE_CLIENT_TABLE = "create table "
            + TABLE_CLIENT + " ("
            + CLIENT_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , "
            + CLIENT_NAME + " TEXT NOT NULL , "
            + CLIENT_LAST_NAME + " TEXT NOT NULL , "
            + CLIENT_ADDRESS + " TEXT, "
            + CLIENT_POSTAL_CODE + " INTEGER, "
            + CLIENT_CITY + " TEXT, "
            + CLIENT_PHONE_NUMBER +" INTEGER NOT NULL , "
            + CLIENT_MAIL + " TEXT); ";

    private static final String CREATE_RENT_TABLE = "create table "
            + TABLE_RENT + " ("
            + RENT_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , "
            + RENT_CLIENT_ID + " INTEGER NOT NULL , "
            + RENT_DATE_RENT + " DATETIME NOT NULL , "
            + RENT_DATE_RETURN + " DATETIME NOT NULL , "
            + RENT_DATE_TRUE_RETURN + " DATETIME, "
            + RENT_PRICE + " INTEGER NOT NULL , "
            + RENT_STATUS + " TEXT NOT NULL ); ";

    private static final String CREATE_RENTED_ITEMS_TABLE = "create table "
            + TABLE_RENTED_ITEMS + " ("
            + RENTED_ITEMS_ID + " integer primary key autoincrement UNIQUE , "
            + RENTED_ITEMS_RENT_ID + " INTEGER NOT NULL ,"
            + RENTED_ITEMS_COSTUME_ID + " INTEGER NOT NULL ,"
            + RENTED_ITEMS_POSITION + " INTEGER NOT NULL ); ";

    public MySQLiteHelper(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database) {
        database.execSQL(CREATE_CLIENT_TABLE);
        database.execSQL(CREATE_COSTUME_TABLE);
        database.execSQL(CREATE_RENT_TABLE);
        database.execSQL(CREATE_RENTED_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COSTUMES);
    }
}
