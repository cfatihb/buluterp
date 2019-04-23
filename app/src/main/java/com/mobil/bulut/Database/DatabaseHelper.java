package com.mobil.bulut.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobil.bulut.Models.Customers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cem on 15/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "customer_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Customers.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Customers.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertCustomer(String firma,String uuid,String username) {

        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Customers.COLUMN_FIRMA, firma);
        values.put(Customers.COLUMN_UUID, uuid);
        values.put(Customers.COLUMN_USERNAME, username);

        // insert row
        long id = db.insert(Customers.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;

    }

    public Customers getCustomer(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Customers.TABLE_NAME,
                new String[]{Customers.COLUMN_ID, Customers.COLUMN_FIRMA, Customers.COLUMN_UUID, Customers.COLUMN_USERNAME},
                Customers.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare beacon object
        Customers beacons = new Customers(
                cursor.getInt(cursor.getColumnIndex(Customers.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Customers.COLUMN_FIRMA)),
                cursor.getString(cursor.getColumnIndex(Customers.COLUMN_UUID)),
                cursor.getString(cursor.getColumnIndex(Customers.COLUMN_USERNAME)));

        // close the db connection
        cursor.close();

        return beacons;
    }

    public List<Customers> getAllCustomers() {

        List<Customers> customers = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Customers.TABLE_NAME + " ORDER BY " + Customers.COLUMN_ID + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Customers customer = new Customers();
                customer.setId(cursor.getInt(cursor.getColumnIndex(Customers.COLUMN_ID)));
                customer.setFirma(cursor.getString(cursor.getColumnIndex(Customers.COLUMN_FIRMA)));
                customer.setUuid(cursor.getString(cursor.getColumnIndex(Customers.COLUMN_UUID)));
                customer.setUsername(cursor.getString(cursor.getColumnIndex(Customers.COLUMN_USERNAME)));

                customers.add(customer);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return beacons list
        return customers;
    }

    public int getBeaconCounts() {
        String countQuery = "SELECT  * FROM " + Customers.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateCustomer(Customers beacons) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Customers.COLUMN_FIRMA, beacons.getFirma());
        values.put(Customers.COLUMN_UUID, beacons.getUuid());
        values.put(Customers.COLUMN_USERNAME, beacons.getUsername());

        // updating row
        return db.update(Customers.TABLE_NAME, values, Customers.COLUMN_ID + " = ?",
                new String[]{String.valueOf(beacons.getId())});
    }

    public void deleteBeacon(Customers customers) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Customers.TABLE_NAME, Customers.COLUMN_ID + " = ?",
                new String[]{String.valueOf(customers.getId())});
        db.close();
    }
}
