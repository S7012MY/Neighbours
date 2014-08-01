package com.androidcamp.neighbors.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.androidcamp.neighbors.R;

/**
 * Created by demouser on 7/31/14.
 */
public class ChatActivity extends Activity {

    protected TextView chatTitle;
    protected EditText messageEdit;
    protected Button sendButton;
    protected ListView messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        chatTitle = (TextView) findViewById(R.id.chat_title);
        messageEdit = (EditText) findViewById(R.id.message_text);
        sendButton = (Button) findViewById(R.id.send_button);
        messagesList = (ListView) findViewById(R.id.messages_list);
    }


}
