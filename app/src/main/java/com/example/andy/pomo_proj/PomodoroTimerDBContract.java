package com.example.andy.pomo_proj;

/**
 * Created by Andy on 3/18/2016.
 */

import android.provider.BaseColumns;

/**
 * The Class PomodoroTimerDBContract. Contains definitions of databases tables.
 */
public final class PomodoroTimerDBContract
{

    /**
     * Instantiates a new object of class.
     */
    public PomodoroTimerDBContract() {}

    /** The Constant COLUMN_ID. */
    public static final String COLUMN_ID = "_id";

    /** The Constant TEXT_TYPE. */
    public static final String TEXT_TYPE = " TEXT";

    /** The Constant INT_TYPE. */
    public static final String INT_TYPE = " INT";

    /** The Constant COMMA_SEP. */
    public static final String COMMA_SEP = ",";

    /**
     * The Class PomodorosTable.
     * Defines fields of database's table "pomodoro".
     */
    public static abstract class PomodorosTable implements BaseColumns
    {

        /** The Constant TABLE_NAME. */
        public static final String TABLE_NAME = "pomodoros";
        /** The Constant COLUMN_NAME_START_DATE. */
        public static final String COLUMN_NAME_START_DATE = "startdate";

        /** The Constant COLUMN_NAME_STOP_DATE. */
        public static final String COLUMN_NAME_STOP_DATE = "stopdate";

        /** The Constant COLUMN_NAME_START_HOUR. */
        public static final String COLUMN_NAME_START_HOUR = "starthour";

        /** The Constant COLUMN_NAME_STOP_HOUR. */
        public static final String COLUMN_NAME_STOP_HOUR = "stophour";

        /** The Constant COLUMN_NAME_NUM_IN_ROW. */
        public static final String COLUMN_NAME_NUM_IN_ROW = "numinrow";

        /** The Constant CREATE_TABLE. */
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME_START_DATE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_STOP_DATE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_START_HOUR + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_STOP_HOUR + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_NUM_IN_ROW + INT_TYPE +
                " )";

        /** The Constant DROP_TABLE. */
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * The Class TagsTable.
     * Defines fields of database's table "tags".
     */
    public static abstract class TagsTable implements BaseColumns
    {

        /** The Constant TABLE_NAME. */
        public static final String TABLE_NAME = "tags";

        /** The Constant COLUMN_NAME_TAG_NAME. */
        public static final String COLUMN_NAME_TAG_NAME = "tagname";

        /** The Constant COLUMN_NAME_LAST_USED. */
        public static final String COLUMN_NAME_LAST_USED = "lastused";

        /** The Constant CREATE_TABLE. */
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME_TAG_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_LAST_USED + TEXT_TYPE +
                " )";

        /** The Constant DROP_TABLE. */
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * The Class PomodorosTagsTable.
     * Defines fields of database's table "pomodorostags".
     */
    public static abstract class PomodorosTagsTable implements BaseColumns
    {

        /** The Constant TABLE_NAME. */
        public static final String TABLE_NAME = "pomodorostags";

        /** The Constant COLUMN_NAME_POMODORO_ID. */
        public static final String COLUMN_NAME_POMODORO_ID = "pomodoroid";

        /** The Constant COLUMN_NAME_TAG_ID. */
        public static final String COLUMN_NAME_TAG_ID = "tagid";

        /** The Constant CREATE_TABLE. */
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME_POMODORO_ID + INT_TYPE + COMMA_SEP +
                COLUMN_NAME_TAG_ID + INT_TYPE +
                " )";

        /** The Constant DROP_TABLE. */
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}