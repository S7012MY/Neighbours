package com.androidcamp.neighbors.db;

import android.content.ContentValues;

import java.sql.Timestamp;

/**
 * Created by julie on 7/31/14.
 */
public class Conversation implements ContentValuesTransformer{
    private String mConversationID;
    private Timestamp mTime;
    private boolean mIsGroup;
    private String mUserId;
    private String mReceiverId;
    private String mMessage;

    public String getConversationID() {
        return mConversationID;
    }

    public void setConversationID(String mConversationID) {
        this.mConversationID = mConversationID;
    }

    public Timestamp getTime() {
        return mTime;
    }

    public void setTime(Timestamp mTime) {
        this.mTime = mTime;
    }

    public boolean isGroup() {
        return mIsGroup;
    }

    public void setIsGroup(boolean mIsGroup) {
        this.mIsGroup = mIsGroup;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getReceiverId() {
        return mReceiverId;
    }

    public void setReceiverId(String mReceiverId) {
        this.mReceiverId = mReceiverId;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(NeighboursContract.ConversationEntry.COLUMN_NAME_ENTRY_ID,this.mConversationID);
        values.put(NeighboursContract.ConversationEntry.COLUMN_NAME_TIME,ConversionHelper.getStringFromTimestamp(this.mTime));
        values.put(NeighboursContract.ConversationEntry.COLUMN_NAME_IS_GROUP,this.mIsGroup);
        values.put(NeighboursContract.ConversationEntry.COLUMN_NAME_USER_ID,this.mUserId);
        values.put(NeighboursContract.ConversationEntry.COLUMN_NAME_RECEIVER_ID,this.mReceiverId);
        values.put(NeighboursContract.ConversationEntry.COLUMN_NAME_MESSAGE,this.mMessage);
        return values;
    }

    @Override
    public Conversation fromContentValues(ContentValues contentValues) {
        this.mConversationID = contentValues.getAsString(NeighboursContract.ConversationEntry.COLUMN_NAME_ENTRY_ID);
        this.mTime = ConversionHelper.getTimestampFromString(contentValues.getAsString(NeighboursContract.ConversationEntry.COLUMN_NAME_TIME));
        this.mIsGroup = contentValues.getAsBoolean(NeighboursContract.ConversationEntry.COLUMN_NAME_IS_GROUP);
        this.mUserId = contentValues.getAsString(NeighboursContract.ConversationEntry.COLUMN_NAME_USER_ID);
        this.mReceiverId = contentValues.getAsString(NeighboursContract.ConversationEntry.COLUMN_NAME_RECEIVER_ID);
        this.mMessage = contentValues.getAsString(NeighboursContract.ConversationEntry.COLUMN_NAME_MESSAGE);
        return this;
    }
}
