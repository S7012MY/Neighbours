package com.androidcamp.neighbors.db;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by julie on 7/31/14.
 */
public class ConversionHelper {
    //TODO: May modify the format of bitmap and the date format

    // convert from bitmap to byte array
    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        if(bitmap == null) {
            return null;
        }
        else {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        }
    }

    // convert from string to bitmap
    public static Bitmap getBitmapFromByteArray(byte[] byteArray) {
        if(byteArray == null)
            return null;
        else
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    //convert from Timestamp to String
    public static String getStringFromTimestamp(Timestamp myTimestamp) {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(myTimestamp);
    }

    //convert from String to Timestamp
    public static Timestamp getTimestampFromString(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Timestamp timestamp = null;
        try {
            Date parsedDate = dateFormat.parse(time);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
}
