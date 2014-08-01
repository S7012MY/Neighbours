package com.androidcamp.neighbors.db;

import android.content.ContentValues;
import android.graphics.Bitmap;

/**
 * Created by julie on 7/31/14.
 */
public class User implements ContentValuesTransformer {
    private String mUserID;
    private String mName;
    private String mMail;
    private String mLocation;
    private Bitmap mImage;
    private String mSex;

    public User() {}

    public User(String mUserID,String mName,String mMail,String mLocation, Bitmap mImage, String mSex) {
        this.mUserID = mUserID;
        this.mName = mName;
        this.mMail = mMail;
        this.mLocation = mLocation;
        this.mImage = mImage;
        this.mSex = mSex;
    }

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String mUserID) {
        this.mUserID = mUserID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getMail() {
        return mMail;
    }

    public void setMail(String mMail) {
        this.mMail = mMail;
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

    public String getSex() {
        return mSex;
    }

    public void setSex(String mSex) {
        this.mSex = mSex;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(NeighboursContract.UserEntry.COLUMN_NAME_ENTRY_ID,this.mUserID);
        values.put(NeighboursContract.UserEntry.COLUMN_NAME_NAME,this.mName);
        values.put(NeighboursContract.UserEntry.COLUMN_NAME_MAIL,this.mMail);
        values.put(NeighboursContract.UserEntry.COLUMN_NAME_LOCATION,this.mLocation);
        values.put(NeighboursContract.UserEntry.COLUMN_NAME_IMAGE,ConversionHelper.getBytesFromBitmap(this.mImage));
        values.put(NeighboursContract.UserEntry.COLUMN_NAME_SEX,this.mSex);
        return values;
    }

    @Override
    public User fromContentValues(ContentValues contentValues) {
        this.mUserID = contentValues.getAsString(NeighboursContract.UserEntry.COLUMN_NAME_ENTRY_ID);
        this.mName = contentValues.getAsString(NeighboursContract.UserEntry.COLUMN_NAME_NAME);
        this.mMail = contentValues.getAsString(NeighboursContract.UserEntry.COLUMN_NAME_MAIL);
        this.mLocation = contentValues.getAsString(NeighboursContract.UserEntry.COLUMN_NAME_LOCATION);
        this.mImage = ConversionHelper.getBitmapFromByteArray(contentValues.getAsByteArray(NeighboursContract.UserEntry.COLUMN_NAME_IMAGE));
        this.mSex = contentValues.getAsString(NeighboursContract.UserEntry.COLUMN_NAME_SEX);
        return this;
    }
}
