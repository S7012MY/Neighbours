package com.androidcamp.neighbors.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by julie on 7/31/14.
 */
public class NeighboursDatabase {
    private SQLiteDatabase mDb;
    private NeighboursDbHelper mDatabaseHelper;

    public NeighboursDatabase(Context context) {
        mDatabaseHelper = new NeighboursDbHelper(context);
        mDb = mDatabaseHelper.getWritableDatabase();
    }

    public void close() {
        mDatabaseHelper.close();
    }


}
