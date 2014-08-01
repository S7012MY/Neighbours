package com.androidcamp.neighbors.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidcamp.neighbors.AdaptersHelper;
import com.androidcamp.neighbors.NeighbourApplication;
import com.androidcamp.neighbors.R;
import com.androidcamp.neighbors.SendingHelper;

/**
 * Created by demouser on 7/31/14.
 */
public class BroadcastChatActivity extends ChatActivity {


    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatTitle.setText(R.string.broadcast_chat_title);
        AdaptersHelper.createBroadcastAdapter(this, messagesList);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userId = extras.getString("user_id");
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEdit.getText().toString();
                SendingHelper.sendMsg(message,userId);
                //AdaptersHelper.addBroadcastMessage(userId + ": " + message);

                messagesList.invalidateViews();
                Log.v(NeighbourApplication.LOG_TAG, "Message sent");
                messageEdit.getText().clear();
            }
        });
    }
}
