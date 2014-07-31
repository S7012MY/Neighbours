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
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_MAIL + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_LOCATION + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_IMAGE + TEXT_TYPE + COMMA_SEP +
            " )";
    private static final String SQL_CREATE_GROUP_TABLE =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_MAIL + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_LOCATION + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_IMAGE + TEXT_TYPE + COMMA_SEP +
                    " )";
    private static final String SQL_CREATE_CONVERSATION_TABLE =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_MAIL + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_LOCATION + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_IMAGE + TEXT_TYPE + COMMA_SEP +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

    /* Inner classes that define the tables' contents */
    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_ENTRY_ID = "user_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_MAIL = "mail";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_IMAGE = "image_path";
    }

    public static abstract class GroupEntry implements BaseColumns {
        public static final String TABLE_NAME = "group";
        public static final String COLUMN_NAME_ENTRY_ID = "group_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_IMAGE = "image_path";
    }

    public static abstract class ConversationEntry implements BaseColumns {
        public static final String TABLE_NAME = "conversation";
        public static final String COLUMN_NAME_ENTRY_ID = "user_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_MAIL = "mail";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_IMAGE = "image_path";
    }
}