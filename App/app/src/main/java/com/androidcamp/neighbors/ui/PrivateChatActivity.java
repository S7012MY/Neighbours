package com.androidcamp.neighbors.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidcamp.neighbors.AdaptersHelper;
import com.androidcamp.neighbors.NeighbourApplication;
import com.androidcamp.neighbors.R;
import com.androidcamp.neighbors.SendingHelper;

/**
 * Created by demouser on 8/1/14.
 */
public class PrivateChatActivity extends ChatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatTitle.setText(R.string.private_chat_title);
        AdaptersHelper.createBroadcastAdapter(this, messagesList, null);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userId = extras.getString("user_id");
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEdit.getText().toString();
                SendingHelper.sendPrivateMsg(message, userId);

                messagesList.invalidateViews();
                Log.v(NeighbourApplication.LOG_TAG, "Message sent");
                messageEdit.getText().clear();
            }
        });
    }

}
