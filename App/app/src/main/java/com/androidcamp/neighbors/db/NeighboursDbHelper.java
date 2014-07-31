package com.androidcamp.neighbors.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by julie on 7/31/14.
 * This class is for database helper
 */
// Create the Helpers
public class NeighboursDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Neighbours.db";

    public NeighboursDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NeighboursContract.SQL_CREATE_USER_TABLE);
        db.execSQL(NeighboursContract.SQL_CREATE_GROUP_TABLE);
        db.execSQL(NeighboursContract.SQL_CREATE_CONVERSATION_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(NeighboursContract.SQL_DELETE_USER_TABLE);
        db.execSQL(NeighboursContract.SQL_DELETE_GROUP_TABLE);
        db.execSQL(NeighboursContract.SQL_DELETE_CONVERSATION_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
