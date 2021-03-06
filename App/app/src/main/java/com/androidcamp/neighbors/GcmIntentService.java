package com.androidcamp.neighbors;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.plus.Plus;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GcmIntentService extends IntentService {

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (extras != null && !extras.isEmpty()) {  // has effect of unparcelling Bundle
            // Since we're not using two way messaging, this is all we really to check for

            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Logger.getLogger("GCM_RECEIVED").log(Level.INFO, extras.toString());
                String msg = extras.getString("message");
                if (msg.split(": ").length > 1) {
                    msg = msg.split(": ")[1];
                }
                if (extras.containsKey("target_user") && extras.getString("target_user") != null
                        && extras.getString("target_user").equals(Plus.AccountApi.getAccountName(NeighbourApplication.sGoogleApiClient))) {
                    AdaptersHelper.addPrivateMessage(extras.getString("user"), msg);
                    Log.v(NeighbourApplication.LOG_TAG, "private");
                } else {
                    AdaptersHelper.addBroadcastMessage(extras.getString("user"), msg);
                    Log.v(NeighbourApplication.LOG_TAG, "broadcast");
                }
                showToast("[" + extras.getString("user") + "]: " + extras.getString("message"));
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    protected void showToast(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }
}