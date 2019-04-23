package com.mobil.bulut.Models;

/**
 * Created by Cem on 20/02/18.
 */

public class Customers {

    public static final String TABLE_NAME = "customers";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRMA = "firma";
    public static final String COLUMN_UUID = "uuid";
    public static final String COLUMN_USERNAME = "username";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_FIRMA + " TEXT,"
                    + COLUMN_UUID + " TEXT,"
                    + COLUMN_USERNAME + " TEXT"
                    + ")";

    private int id;
    private String firma;
    private String uuid;
    private String username;

    public Customers() {
    }

    public Customers(int id, String firma, String uuid, String username) {
        this.id = id;
        this.firma = firma;
        this.uuid = uuid;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
