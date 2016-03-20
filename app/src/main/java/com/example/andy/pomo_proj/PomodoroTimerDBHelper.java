package com.example.andy.pomo_proj;

import android.content.Context;

/**
 * Created by Andy on 3/18/2016.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * The Class PomodoroTimerDBHelper.
 * Used for defining the database constants (e.g. database name) and CRUD (Create, Read, Update, Delete) operations.
 */
public class PomodoroTimerDBHelper extends SQLiteOpenHelper
{

    /** The Constant DATABASE_NAME. */
    public static final String DATABASE_NAME = "PomodoroTimerDB.db";

    /** The Constant DATABASE_VERSION. */
    public static final int DATABASE_VERSION = 1;

    private static final String TAG_DB = "Database";

    /**
     * Instantiates a PomodoroTimerDBHelper class object.
     *
     * @param context the context
     */
    public PomodoroTimerDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(PomodoroTimerDBContract.PomodorosTable.CREATE_TABLE);
        db.execSQL(PomodoroTimerDBContract.TagsTable.CREATE_TABLE);
        db.execSQL(PomodoroTimerDBContract.PomodorosTagsTable.CREATE_TABLE);
        Log.i(TAG_DB,"Database created.");
        Log.i(TAG_DB,"Tables created.");

        TagClass tag0 = new TagClass(0,"Pierwszy tag","0000-00-00 00:00:00");
        TagClass tag1 = new TagClass(1,"Praca","0000-00-00 00:00:00");
        TagClass tag2 = new TagClass(2, "Dom", "0000-00-00 00:00:00");
        TagClass tag3 = new TagClass(2, "Nauka", "0000-00-00 00:00:00");
        TagClass tag4 = new TagClass(4, "Prezentacja", "0000-00-00 00:00:00");
        TagClass tag5 = new TagClass(5, "Inne", "0000-00-00 00:00:00");

        addTag(tag0, db);
        addTag(tag1,db);
        addTag(tag2,db);
        addTag(tag3,db);
        addTag(tag4,db);
        addTag(tag5,db);
        Log.i(TAG_DB, "Sample tags created.");

    }

    /**
     * Defined operations when database's version is upgraded.
     * Simply drops database and re-creates it.
     *
     * @param db the database
     * @param oldVersion the old version of database
     * @param newVersion the new version of database
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i(TAG_DB, "Upgrading database...");
        Log.i(TAG_DB, "Removing tables...");
        db.execSQL(PomodoroTimerDBContract.PomodorosTable.DROP_TABLE);
        db.execSQL(PomodoroTimerDBContract.TagsTable.DROP_TABLE);
        db.execSQL(PomodoroTimerDBContract.PomodorosTagsTable.DROP_TABLE);
        Log.i(TAG_DB, "Creating new tables...");
        onCreate(db);
        Log.i(TAG_DB, "Done.");

    }

    /**
     * Adds the pomodoro object to the database. Returns pomodoro's id.
     *
     * @param pomodoro the pomodoro object
     * @return the long pomodoro's id
     */
    public long addPomodoro(PomodoroClass pomodoro)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PomodoroTimerDBContract.PomodorosTable.COLUMN_NAME_START_DATE, pomodoro.getStartDate());
        values.put(PomodoroTimerDBContract.PomodorosTable.COLUMN_NAME_STOP_DATE, pomodoro.getStopDate());
        values.put(PomodoroTimerDBContract.PomodorosTable.COLUMN_NAME_START_HOUR, pomodoro.getStartHour());
        values.put(PomodoroTimerDBContract.PomodorosTable.COLUMN_NAME_STOP_HOUR, pomodoro.getStopHour());
        values.put(PomodoroTimerDBContract.PomodorosTable.COLUMN_NAME_NUM_IN_ROW, pomodoro.getNumInRow());

        long id = db.insert(PomodoroTimerDBContract.PomodorosTable.TABLE_NAME, null, values);

        Log.i(TAG_DB, "Pomodoro added!");
        return id;
    }

    /**
     * Adds the tag object to the database. Returns tag's id.
     *
     * @param tag the tag object
     * @param db the database
     * @return the long tag's id
     */
    public long addTag(TagClass tag, SQLiteDatabase db)
    {
        ContentValues values = new ContentValues();
        values.put(PomodoroTimerDBContract.TagsTable.COLUMN_NAME_TAG_NAME, tag.getTagName());
        values.put(PomodoroTimerDBContract.TagsTable.COLUMN_NAME_LAST_USED, tag.getLastUsed());
        long id = db.insert(PomodoroTimerDBContract.TagsTable.TABLE_NAME, null, values);

        Log.i(TAG_DB, "Tag added!");
        return id;
    }

    /**
     * Adds the pomodoros-tags object to the database. Returns pomodoro's tag's id.
     *
     * @param pomodorosTag the pomodoro's tag object
     * @return the long pomodoros tag's id.
     */
    public long addPomodorosTag(PomodorosTagClass pomodorosTag)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PomodoroTimerDBContract.PomodorosTagsTable.COLUMN_NAME_POMODORO_ID, pomodorosTag.getPomodoroID());
        values.put(PomodoroTimerDBContract.PomodorosTagsTable.COLUMN_NAME_TAG_ID, pomodorosTag.getTagID());

        long id = db.insert(PomodoroTimerDBContract.PomodorosTagsTable.TABLE_NAME, null, values);

        Log.i(TAG_DB, "Pomodoro's Tag added!");
        return id;
    }

    /**
     * Gets the pomodoro selected by id from the database.
     *
     * @param dbID the pomodoro's id
     * @return the pomodoro object
     */
    public PomodoroClass getPomodoro(long dbID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PomodoroTimerDBContract.PomodorosTable.TABLE_NAME, null, PomodoroTimerDBContract.PomodorosTable._ID + "=?",
                new String[] { String.valueOf(dbID) }, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        PomodoroClass pomodoro = new PomodoroClass(dbID, cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), Integer.parseInt(cursor.getString(5)));


        return pomodoro;
    }

    /**
     * Gets all the pomodoros from the database.
     *
     * @return all the pomodoros list
     */
    public List<PomodoroClass> getAllPomodoros()
    {
        List<PomodoroClass> list = new ArrayList<PomodoroClass>();
        String selectQuery = "SELECT * FROM " + PomodoroTimerDBContract.PomodorosTable.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PomodoroClass pomodoro = new PomodoroClass(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), Integer.parseInt(cursor.getString(5)));
                // Adding contact to list
                list.add(pomodoro);
            } while (cursor.moveToNext());
        }

        return list;
    }

    /**
     * Gets the tag selected by id from the database.
     *
     * @param id the tag's id
     * @return the tag object
     */
    public TagClass getTag(long id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PomodoroTimerDBContract.TagsTable.TABLE_NAME, null, PomodoroTimerDBContract.TagsTable._ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        TagClass tag = new TagClass(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));


        return tag;
    }

    /**
     * Gets all the tags from the database.
     *
     * @return all the tags list
     */
    public List<TagClass> getAllTags()
    {
        List<TagClass> list = new ArrayList<TagClass>();
        String selectQuery = "SELECT * FROM " + PomodoroTimerDBContract.TagsTable.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TagClass tag = new TagClass(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
                list.add(tag);
            } while (cursor.moveToNext());
        }


        return list;
    }

    /**
     * Gets the all pomodoros tags.
     *
     * @return the all pomodoros tags
     */
    public List<PomodorosTagClass> getAllPomodorosTags()
    {
        List<PomodorosTagClass> list = new ArrayList<PomodorosTagClass>();
        String selectQuery = "SELECT * FROM " + PomodoroTimerDBContract.PomodorosTagsTable.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PomodorosTagClass pomodorosTag = new PomodorosTagClass(Long.parseLong(cursor.getString(0)),Long.parseLong(cursor.getString(1)),Long.parseLong(cursor.getString(2)));
                list.add(pomodorosTag);
            } while (cursor.moveToNext());
        }


        return list;
    }

    /**
     * Gets all the tags of pomodoro selected by id from the database.
     *
     * @param id the pomodoro tag's id
     * @return the pomodoro's tags list
     */
    public List<TagClass> getPomodorosTags(long id)
    {
        List<TagClass> list = new ArrayList<TagClass>();

        String selectQuery = "SELECT * FROM " + PomodoroTimerDBContract.PomodorosTagsTable.TABLE_NAME + " WHERE " + PomodoroTimerDBContract.PomodorosTagsTable.COLUMN_NAME_POMODORO_ID + "=?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(id)});

        if(cursor.moveToFirst())
        {
            Cursor newCursor;
            do {
                String newID = cursor.getString(2);
                //reading tag info
                String selectQ = "SELECT * FROM " + PomodoroTimerDBContract.TagsTable.TABLE_NAME + " WHERE " + PomodoroTimerDBContract.TagsTable._ID + "=?";

                newCursor = db.rawQuery(selectQ, new String[] {newID});
                newCursor.moveToFirst();

                TagClass tag = new TagClass(Integer.parseInt(newCursor.getString(0)), newCursor.getString(1), newCursor.getString(2));
                list.add(tag);

            } while(cursor.moveToNext());
        }


        return list;
    }

    /**
     * Gets the last used tags from the database.
     *
     * @param quantity the quantity of records to return
     * @return the tag's last used date
     */
    public List<TagClass> getLastUsedTags(int quantity)
    {
        List<TagClass> list = new ArrayList<TagClass>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + PomodoroTimerDBContract.TagsTable.TABLE_NAME + " order by datetime(" + PomodoroTimerDBContract.TagsTable.COLUMN_NAME_LAST_USED + ") DESC ";

        if(quantity > 0)
        {
            selectQuery = selectQuery +  "LIMIT " +String.valueOf(quantity);
        }

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst())
        {
            do {
                TagClass tag = new TagClass(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
                list.add(tag);
            } while(cursor.moveToNext());
        }

        return list;
    }

    /**
     * Update the pomodoro in the database.
     *
     * @param pomodoro the pomodoro object
     * @return the update's return status
     */
    public int updatePomodoro(PomodoroClass pomodoro)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PomodoroTimerDBContract.PomodorosTable.COLUMN_NAME_START_DATE, pomodoro.getStartDate());
        values.put(PomodoroTimerDBContract.PomodorosTable.COLUMN_NAME_STOP_DATE, pomodoro.getStopDate());
        values.put(PomodoroTimerDBContract.PomodorosTable.COLUMN_NAME_START_HOUR, pomodoro.getStartHour());
        values.put(PomodoroTimerDBContract.PomodorosTable.COLUMN_NAME_STOP_HOUR, pomodoro.getStopHour());
        values.put(PomodoroTimerDBContract.PomodorosTable.COLUMN_NAME_NUM_IN_ROW, pomodoro.getNumInRow());

        int rowsAffected = db.update(PomodoroTimerDBContract.PomodorosTable.TABLE_NAME, values, PomodoroTimerDBContract.PomodorosTable._ID + " = ?",
                new String[] { String.valueOf(pomodoro.getID()) });

        Log.i(TAG_DB, "Pomodoro upgraded");
        return rowsAffected;
    }

    /**
     * Update the tag in the database.
     *
     * @param tag the tag object
     * @return the update's return status
     */
    public int updateTag(TagClass tag)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PomodoroTimerDBContract.TagsTable.COLUMN_NAME_TAG_NAME, tag.getTagName());
        values.put(PomodoroTimerDBContract.TagsTable.COLUMN_NAME_LAST_USED, tag.getLastUsed());

        int rowsAffected = db.update(PomodoroTimerDBContract.TagsTable.TABLE_NAME, values, PomodoroTimerDBContract.TagsTable._ID + " = ?",
                new String[] { String.valueOf(tag.getID()) });

        Log.i(TAG_DB, "Tag updated");
        return rowsAffected;
    }

    /**
     * Update the pomodoro's tag in the database.
     *
     * @param pomodorosTag the pomodoros tag object
     * @return update's return status
     */
    public int updatePomodorosTag(PomodorosTagClass pomodorosTag)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PomodoroTimerDBContract.PomodorosTagsTable.COLUMN_NAME_POMODORO_ID, pomodorosTag.getPomodoroID());
        values.put(PomodoroTimerDBContract.PomodorosTagsTable.COLUMN_NAME_TAG_ID, pomodorosTag.getTagID());

        int rowsAffected = db.update(PomodoroTimerDBContract.PomodorosTagsTable.TABLE_NAME, values, PomodoroTimerDBContract.PomodorosTagsTable._ID + " = ?",
                new String[] { String.valueOf(pomodorosTag.getID()) });

        Log.i("Datavase", "Pomodoro's tag updated");
        return rowsAffected;
    }

    /**
     * Delete the pomodoro from the database selected by it's id.
     *
     * @param id the pomodoro's id
     */
    public void deletePomodoro(long id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PomodoroTimerDBContract.PomodorosTable.TABLE_NAME, PomodoroTimerDBContract.PomodorosTable._ID + " = ?",
                new String[] { String.valueOf(id) });

        Log.i(TAG_DB, "Pomodoro removed");
    }

    /**
     * Delete the tag from the database selected by it's id.
     *
     * @param id the tag's id
     */
    public void deleteTag(long id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PomodoroTimerDBContract.TagsTable.TABLE_NAME, PomodoroTimerDBContract.TagsTable._ID + " = ?",
                new String[] { String.valueOf(id) });

        Log.i(TAG_DB, "Tag removed");
    }

    /**
     * Delete pomodoro's tag from the database selected by it's id.
     *
     * @param id the pomodoro tag's id
     */
    public void deletePomodorosTag(long id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PomodoroTimerDBContract.PomodorosTagsTable.TABLE_NAME, PomodoroTimerDBContract.PomodorosTagsTable._ID + " = ?",
                new String[] { String.valueOf(id) });

        Log.i("Datavase", "Pomodoro's tag removed");
    }

    /**
     * Closes db if exist.
     */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
