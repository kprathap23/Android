package com.pratap.sample1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.pratap.sample1.models.Contact;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Contacts.db";

    // Contacts table name
    private static final String TABLE_CONTACTS = "Contacts";


    // Contacts Table Columns names
    private static final String KEY_SNO = "Sno";
    private static final String KEY_GIDID = "GIDID";
    private static final String KEY_FULLNAME = "FullName";
    private static final String KEY_MOBILENUMBER = "MobileNumber";
    private static final String KEY_PERSONALEMAILID = "PEmailId";
    private static final String KEY_TEXTDRAWCOLOR = "TextDrawColor";

    private static DatabaseHandler dbHander = null;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHandler getDbInstance(Context contex) {

        if (dbHander != null) {
            return dbHander;
        } else {
            return new DatabaseHandler(contex);
        }

    }


    public void deleteSingleContactRecord(String gidId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_GIDID + " = ?",
                new String[]{gidId});
        db.close();
    }


    public int updateSingleContactRecord(Contact singleContact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_GIDID, singleContact.getGidId());
        values.put(KEY_FULLNAME, singleContact.getFullName());
        values.put(KEY_MOBILENUMBER, singleContact.getMobileNumber());
        values.put(KEY_PERSONALEMAILID, singleContact.getPersonalEmailId());
        values.put(KEY_TEXTDRAWCOLOR, String.valueOf(singleContact.getTextDrawbleColor()));
        int val = db.update(TABLE_CONTACTS, values, KEY_GIDID + " = ?",
                new String[] { String.valueOf(singleContact.getGidId()) });
        db.close();
        return val;

    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_SELLTIS_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_CONTACTS + "("
                + KEY_SNO + " INTEGER PRIMARY KEY,"
                + KEY_GIDID + " TEXT,"
                + KEY_FULLNAME + " TEXT,"
                + KEY_MOBILENUMBER + " TEXT,"
                + KEY_PERSONALEMAILID + " TEXT,"
                + KEY_TEXTDRAWCOLOR + " TEXT"
                + ")";


        db.execSQL(CREATE_SELLTIS_TABLE);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        onCreate(db);
    }




    /*public boolean checkFilter(String gidid) {
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " +
                TABLE_Filters + " WHERE " + KEY_GID_ID + " =? ";
        Cursor cursor = db.rawQuery(selectString, new String[]{gidid});

        boolean hasFilter = false;
        if (cursor.moveToFirst()) {
            hasFilter =
                    true;

            Log.i("Selltis FilterCheck", "Filter already exitst");
        }
        cursor.close(); // Don't forget to close your cursor // AND your  Database!


        return hasFilter;
    }*/


    public long addNewContact(Contact singContact) {
        SQLiteDatabase db = this.getWritableDatabase();
        //checkFilter(Gid


        ContentValues values = new ContentValues();
        values.put(KEY_GIDID, singContact.getGidId());
        values.put(KEY_FULLNAME, singContact.getFullName());
        values.put(KEY_MOBILENUMBER, singContact.getMobileNumber());
        values.put(KEY_PERSONALEMAILID, singContact.getPersonalEmailId());

        values.put(KEY_TEXTDRAWCOLOR, String.valueOf(singContact.getTextDrawbleColor()));
        // values.put(KEY_CREATEDON, getPresentDatetime());


        long id = db.insert(TABLE_CONTACTS, null, values);
        Log.i("New Contact ADDED", "" + id);

        db.close(); // Closing database connection

        return id;


    }


    public List<Object> getAllContacts() {
        List<Object> allContacts = new ArrayList<Object>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact singleContact = new Contact();
                singleContact.setGidId(cursor.getString(1));
                singleContact.setFullName(cursor.getString(2));
                singleContact.setMobileNumber(cursor.getString(3));
                singleContact.setPersonalEmailId(cursor.getString(4));
                singleContact.setTextDrawbleColor(Integer.parseInt(cursor.getString(5)));

                // Adding contact to list
                allContacts.add(singleContact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        Log.e("Selltis Contacts count ", allContacts.size() + "");
        // return contact list
        return allContacts;

    }










  /*// Check mailGid exists or not
    public boolean checkLogMailGuid(String gidid) {
        SQLiteDatabase db =
                getWritableDatabase();
        String selectString = "SELECT * FROM " +
                KEY_TABLE_LOGMAIL + " WHERE " + KEY_MAIL_ID + " =? ";
        Cursor cursor =
                db.rawQuery(selectString, new String[]{gidid});
        // add // the //  String // your // searching // by // here

        boolean hasGUID = false;
        if (cursor.moveToFirst()) {
            hasGUID =
                    true;

            Log.i("MAIL SQL CHECK", "MAIL already exitst");
        }
        cursor.close(); // Don't forget to close your cursor // AND your Database!

        return hasGUID;


    }*/


}