package com.androidcamp.neighbors.com.androidcamp.neighbors.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.androidcamp.neighbors.GcmRegistrationAsyncTask;
import com.androidcamp.neighbors.R;
import com.example.mymodule.neighborsbackend.messaging.Messaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;


public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        new GcmRegistrationAsyncTask().execute(this);
        sendMsg();
       // new GcmMessageAsyncTask().execute(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendMsg() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


        Messaging.Builder builder = new Messaging.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
        Messaging endpoint = builder.build();
                try {
                    endpoint.messagingEndpoint().sendMessage("sad").execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
            }
}
