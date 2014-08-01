package com.androidcamp.neighbors.db;

import android.content.ContentValues;

/**
 * Created by demouser on 7/31/14.
 */
public interface ContentValuesTransformer {

    ContentValues toContentValues();

    Object fromContentValues(ContentValues contentValues);
}
