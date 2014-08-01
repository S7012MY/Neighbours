package com.androidcamp.neighbors;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by demouser on 8/1/14.
 */
public class AdaptersHelper {

    private static MessageListAdapter mBroadcastAdapter;

    //private static MessageListAdapter mPrivateAdapters;

    //private static MessageListAdapter mGroupAdapters;


    public static MessageListAdapter getBroadcastAdapter() {
        return mBroadcastAdapter;
    }

    public static void createBroadcastAdapter(Activity activity, ListView parentView) {
        AdaptersHelper.mBroadcastAdapter = new MessageListAdapter(activity, parentView);
        parentView.setAdapter(AdaptersHelper.mBroadcastAdapter);
    }

    public static void addBroadcastMessage(String message) {
        if (mBroadcastAdapter == null) {
            Log.w(NeighbourApplication.LOG_TAG, "Received message but broadcast adapter is not initialized");
            return;
        }
        mBroadcastAdapter.addMessage(message);

    }


    public static class MessageListAdapter extends BaseAdapter {

        private ArrayList<String> messages;

        private Activity mActivity;

        private ListView mParentView;

        private MessageListAdapter(Activity activity, ListView listView) {
            messages = new ArrayList<String>();
            mActivity = activity;
            mParentView = listView;
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
            TextView textView;
            if (convertView == null || !(convertView instanceof TextView)) {
                textView = (TextView) mActivity.getLayoutInflater().inflate(R.layout.message_view, parent, false);
            } else {
                if (Integer.valueOf(position).equals(convertView.getTag())) {
                    return convertView;
                }
                textView = (TextView) convertView;
            }

            textView.setText(messages.get(position));
            textView.setTag(Integer.valueOf(position));
            return textView;
        }

        private void addMessage(String msg) {
            messages.add(msg);
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }
}
