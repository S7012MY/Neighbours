package com.androidcamp.neighbors.db;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by julie on 7/31/14.
 */
public class Group implements ContentValuesTransformer{

    private String mGroupID;
    private String mName;
    private String mLocation;
    private Bitmap mImage;

    public String getGroupID() {
        return mGroupID;
    }

    public void setGroupID(String mGroupID) {
        this.mGroupID = mGroupID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap mImage) {
        this.mImage = mImage;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(NeighboursContract.GroupEntry.COLUMN_NAME_ENTRY_ID,this.mGroupID);
        values.put(NeighboursContract.GroupEntry.COLUMN_NAME_NAME,this.mName);
        values.put(NeighboursContract.GroupEntry.COLUMN_NAME_LOCATION,this.mLocation);
        values.put(NeighboursContract.GroupEntry.COLUMN_NAME_IMAGE,ConversionHelper.getBytesFromBitmap(this.mImage));
        return values;
    }

    @Override
    public Object fromContentValues(ContentValues contentValues) {
        this.mGroupID = contentValues.getAsString(NeighboursContract.GroupEntry.COLUMN_NAME_ENTRY_ID);
        this.mName = contentValues.getAsString(NeighboursContract.GroupEntry.COLUMN_NAME_NAME);
        this.mLocation = contentValues.getAsString(NeighboursContract.GroupEntry.COLUMN_NAME_LOCATION);
        this.mImage = ConversionHelper.getBitmapFromByteArray(contentValues.getAsByteArray(NeighboursContract.GroupEntry.COLUMN_NAME_IMAGE));
        return this;
    }


}
