package com.androidcamp.neighbors;

import android.location.Location;

import com.example.mymodule.neighborsbackend.messaging.Messaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by demouser on 7/31/14.
 */
public class SendingHelper {

    public static void sendMsg(final String message, final String userId) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                Messaging.Builder builder = new Messaging.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
                Messaging endpoint = builder.build();
                try {
                    endpoint.messagingEndpoint().sendMessage(message, userId).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    Location m;
                }
            }
        });
        t.start();
    }
}
