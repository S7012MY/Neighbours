package com.androidcamp.neighbors.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidcamp.neighbors.Actions;
import com.androidcamp.neighbors.AdaptersHelper;
import com.androidcamp.neighbors.NeighbourApplication;
import com.androidcamp.neighbors.R;
import com.androidcamp.neighbors.SendingHelper;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by demouser on 7/31/14.
 */
public class BroadcastChatActivity extends ChatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatTitle.setText(R.string.broadcast_chat_title);
        AdaptersHelper.createBroadcastAdapter(this, messagesList);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEdit.getText().toString();
                SendingHelper.sendMsg(message);
                AdaptersHelper.addBroadcastMessage(message);

                messagesList.invalidateViews();
                Log.v(NeighbourApplication.LOG_TAG, "Message sent");
                messageEdit.getText().clear();
            }
        });
    }
}
