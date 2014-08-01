package com.androidcamp.neighbors.db;

import android.provider.BaseColumns;

/**
 * Created by ann on 7/31/14.
 *
 * Contract class for defining database.
 */
public final class NeighboursContract {

    public NeighboursContract() {}

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String IMAGE_TYPE = " BLOB";

    public static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_MAIL + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_LOCATION + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_IMAGE + IMAGE_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_SEX + TEXT_TYPE +
            " )";
    public static final String SQL_CREATE_GROUP_TABLE =
            "CREATE TABLE " + GroupEntry.TABLE_NAME + " (" +
                    GroupEntry._ID + " INTEGER PRIMARY KEY," +
                    GroupEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    GroupEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    GroupEntry.COLUMN_NAME_IMAGE + IMAGE_TYPE + COMMA_SEP +
                    GroupEntry.COLUMN_NAME_LOCATION + TEXT_TYPE +
                    " )";
    public static final String SQL_CREATE_CONVERSATION_TABLE =
            "CREATE TABLE " + ConversationEntry.TABLE_NAME + " (" +
                    ConversationEntry._ID + " INTEGER PRIMARY KEY," +
                    ConversationEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    ConversationEntry.COLUMN_NAME_TIME + TEXT_TYPE + COMMA_SEP +
                    ConversationEntry.COLUMN_NAME_IS_GROUP + INTEGER_TYPE + COMMA_SEP +
                    ConversationEntry.COLUMN_NAME_USER_ID + TEXT_TYPE + COMMA_SEP +
                    ConversationEntry.COLUMN_NAME_RECEIVER_ID + TEXT_TYPE + COMMA_SEP +
                    ConversationEntry.COLUMN_NAME_MESSAGE + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_USER_TABLE =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

    public static final String SQL_DELETE_GROUP_TABLE =
            "DROP TABLE IF EXISTS " + GroupEntry.TABLE_NAME;

    public static final String SQL_DELETE_CONVERSATION_TABLE =
            "DROP TABLE IF EXISTS " + ConversationEntry.TABLE_NAME;

    /* Inner classes that define the tables' contents */
    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_ENTRY_ID = "user_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_MAIL = "mail";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_SEX = "sex";
    }

    public static abstract class GroupEntry implements BaseColumns {
        public static final String TABLE_NAME = "groups";
        public static final String COLUMN_NAME_ENTRY_ID = "group_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_IMAGE = "image";
    }

    public static abstract class ConversationEntry implements BaseColumns {
        public static final String TABLE_NAME = "conversation";
        public static final String COLUMN_NAME_ENTRY_ID = "conversation_id";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_IS_GROUP = "is_group";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_RECEIVER_ID = "receiver_id";
        public static final String COLUMN_NAME_MESSAGE = "message";
    }
}