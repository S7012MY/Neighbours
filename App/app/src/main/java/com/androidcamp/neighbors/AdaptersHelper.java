package com.androidcamp.neighbors;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.plus.Plus;

import java.util.ArrayList;

/**
 * Created by demouser on 8/1/14.
 */
public class AdaptersHelper {

    private static MessageListAdapter mBroadcastAdapter;

    private static MessageListAdapter mPrivateAdapter;

    private static MessageListAdapter mPrivateChatListAdapter;


    public static MessageListAdapter getBroadcastAdapter() {
        return mBroadcastAdapter;
    }

    public static void createBroadcastAdapter(Activity activity, ListView parentView, Intent onClickSendIntent) {
        AdaptersHelper.mBroadcastAdapter = new MessageListAdapter(activity, onClickSendIntent);
        parentView.setAdapter(AdaptersHelper.mBroadcastAdapter);
    }

    public static void addBroadcastMessage(String userId, String message) {
        if (mBroadcastAdapter == null) {
            Log.w(NeighbourApplication.LOG_TAG, "Received message but broadcast adapter is not initialized");
            return;
        }
        mBroadcastAdapter.addMessage(userId, message);

    }

    public static MessageListAdapter getPrivateAdapter() {
        return mPrivateAdapter;
    }

    public static void createPrivateAdapter(Activity activity, ListView parentView) {
        AdaptersHelper.mPrivateAdapter = new MessageListAdapter(activity, null);
        parentView.setAdapter(AdaptersHelper.mPrivateAdapter);
    }

    public static void addPrivateMessage(String userId, String message) {
        if (mPrivateAdapter == null) {
            Log.w(NeighbourApplication.LOG_TAG, "Received message but private adapter is not initialized");
            return;
        }
        mPrivateAdapter.addMessage(userId, message);

    }


    public static class MessageListAdapter extends BaseAdapter {

        protected ArrayList<Message> messages;

        protected Activity mActivity;

        protected Intent mOnClickSendIntent;

        protected MessageListAdapter(Activity activity, Intent onClickSendIntent) {
            messages = new ArrayList<Message>();
            mActivity = activity;
            mOnClickSendIntent = onClickSendIntent;
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            return messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TextView userName;
            TextView message;
            if (convertView == null) {
                convertView = mActivity.getLayoutInflater().inflate(R.layout.message_view, parent, false);
                userName = (TextView) convertView.findViewById(R.id.user_name);
                message  = (TextView) convertView.findViewById(R.id.message_text);
            } else {
                if (Integer.valueOf(position).equals(convertView.getTag())) {
                    return convertView;
                }
                userName = (TextView) convertView.findViewById(R.id.user_name);
                message  = (TextView) convertView.findViewById(R.id.message_text);
            }

            if (mOnClickSendIntent != null && userName != null
                    && userName.equals(Plus.AccountApi.getAccountName(NeighbourApplication.sGoogleApiClient))) {
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mOnClickSendIntent);
                        intent.putExtra("user_id", userName.getText().toString());
                        mActivity.startActivity(intent);
                    }
                });
            }

            userName.setText(messages.get(position).getUserName());
            message.setText(messages.get(position).getMessageText());
            message.setTag(Integer.valueOf(position));
            return convertView;
        }

        protected void addMessage(String user, String msg) {
            messages.add(new Message(user, msg));
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }

    public static class Message {
        private String mUserName;
        private String mMessageText;

        public Message(String userName, String messageText) {
            mUserName = userName;
            mMessageText = messageText;
        }

        public String getUserName() {
            return mUserName;
        }

        public void setUserName(String userName) {
            this.mUserName = userName;
        }

        public String getMessageText() {
            return mMessageText;
        }

        public void setMessageText(String messageText) {
            this.mMessageText = messageText;
        }
    }
}
