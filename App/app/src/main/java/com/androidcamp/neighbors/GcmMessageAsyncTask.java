package com.androidcamp.neighbors;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by demouser on 31/07/2014.
 */
public class GcmMessageAsyncTask extends AsyncTask<Context, Void, String> {
    private GoogleCloudMessaging gcm;
    private Context context;

    private static final String SENDER_ID = "810467926240";

    public GcmMessageAsyncTask() {

    }

    @Override
    protected String doInBackground(Context... params) {
        context = params[0];
        String msg = "";

        if (gcm == null) {
            gcm = GoogleCloudMessaging.getInstance(context);
        }
        try {
            Bundle data = new Bundle();
            data.putString("my_message", "Hello World");
            gcm.send(SENDER_ID + "@gcm.googleapis.com", "12", data);

        } catch (IOException e) {
            msg = "Error :" + e.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Logger.getLogger("MESSAGING").log(Level.INFO, msg);
    }
}
