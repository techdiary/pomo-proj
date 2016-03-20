package com.example.andy.pomo_proj;

/**
 * Created by Andy on 3/18/2016.
 */
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

//TODO: Logs. Javadoc.
/**
 * The Class PomodoroContentProvider. Implements ContentProvider for application.
 */
public class PomodoroContentProvider extends ContentProvider {

    /** The database. */
    private PomodoroTimerDBHelper database;

    /** The Constant POMODOROS. */
    private static final int POMODOROS = 10;

    /** The Constant TAGS. */
    private static final int TAGS = 20;

    /** The Constant POMODOROSTAGS. */
    private static final int POMODOROSTAGS = 30;

    /** The Constant AUTHORITY. */
    private static final String AUTHORITY = "com.example.andy.pomo_proj";

    /** The Constant POMODOROS_PATH. */
    private static final String POMODOROS_PATH = "pomodoros";

    /** The Constant TAGS_PATH. */
    private static final String TAGS_PATH = "tags";

    /** The Constant POMODOROSTAGS_PATH. */
    private static final String POMODOROSTAGS_PATH = "pomodorostags";

    /** The Constant CONTENT_URI_POMODOROS. */
    public static final Uri CONTENT_URI_POMODOROS = Uri.parse("content://"
            + AUTHORITY + "/" + POMODOROS_PATH);

    /** The Constant CONTENT_URI_TAGS. */
    public static final Uri CONTENT_URI_TAGS = Uri.parse("content://"
            + AUTHORITY + "/" + TAGS_PATH);

    /** The Constant CONTENT_URI_POMODOROSTAGS. */
    public static final Uri CONTENT_URI_POMODOROSTAGS = Uri.parse("content://"
            + AUTHORITY + "/" + POMODOROSTAGS_PATH);

    /** The Constant CONTENT_TYPE_POMODOROS. */
    public static final String CONTENT_TYPE_POMODOROS = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/pomodoros";

    /** The Constant CONTENT_TYPE_TAGS. */
    public static final String CONTENT_TYPE_TAGS = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/tags";

    /** The Constant CONTENT_TYPE_POMODOROSTAGS. */
    public static final String CONTENT_TYPE_POMODOROSTAGS = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/pomodorostags";

    /** The Constant sURIMatcher. */
    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, POMODOROS_PATH, POMODOROS);
        sURIMatcher.addURI(AUTHORITY, TAGS_PATH, TAGS);
        sURIMatcher.addURI(AUTHORITY, POMODOROSTAGS_PATH, POMODOROSTAGS);
    }

    /* (non-Javadoc)
     * @see android.content.ContentProvider#onCreate()
     */
    @Override
    public boolean onCreate() {
        database = new PomodoroTimerDBHelper(getContext());
        return false;
    }

    /* (non-Javadoc)
     * @see android.content.ContentProvider#query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String)
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case POMODOROS:
                queryBuilder.setTables(PomodoroTimerDBContract.PomodorosTable.TABLE_NAME);
                break;
            case TAGS:
                queryBuilder.setTables(PomodoroTimerDBContract.TagsTable.TABLE_NAME);
                break;
            case POMODOROSTAGS:
                queryBuilder.setTables(PomodoroTimerDBContract.PomodorosTagsTable.TABLE_NAME);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    /* (non-Javadoc)
     * @see android.content.ContentProvider#getType(android.net.Uri)
     */
    @Override
    public String getType(Uri uri) {
        return null;
    }

    /* (non-Javadoc)
     * @see android.content.ContentProvider#insert(android.net.Uri, android.content.ContentValues)
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        String uriPath = "";
        long id = 0;
        switch (uriType) {
            case POMODOROS:
                id = sqlDB.insert(PomodoroTimerDBContract.PomodorosTable.TABLE_NAME, null, values);
                uriPath = POMODOROS_PATH;
                break;
            case TAGS:
                id = sqlDB.insert(PomodoroTimerDBContract.TagsTable.TABLE_NAME, null, values);
                uriPath = TAGS_PATH;
                break;
            case POMODOROSTAGS:
                id = sqlDB.insert(PomodoroTimerDBContract.PomodorosTagsTable.TABLE_NAME, null, values);
                uriPath = POMODOROSTAGS_PATH;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(uriPath + "/" + id);
    }

    /* (non-Javadoc)
     * @see android.content.ContentProvider#delete(android.net.Uri, java.lang.String, java.lang.String[])
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = database.getWritableDatabase();
        int delCount = 0;
        switch (uriType) {
            case POMODOROS:
                delCount = db.delete(PomodoroTimerDBContract.PomodorosTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            case TAGS:
                delCount = db.delete(PomodoroTimerDBContract.TagsTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            case POMODOROSTAGS:
                delCount = db.delete(PomodoroTimerDBContract.PomodorosTagsTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        if (delCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return delCount;
    }

    /* (non-Javadoc)
     * @see android.content.ContentProvider#update(android.net.Uri, android.content.ContentValues, java.lang.String, java.lang.String[])
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case POMODOROS:
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(PomodoroTimerDBContract.PomodorosTable.TABLE_NAME,
                            values, selection, selectionArgs);
                }
                break;
            case TAGS:
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(PomodoroTimerDBContract.TagsTable.TABLE_NAME,
                            values, selection, selectionArgs);
                }
                break;
            case POMODOROSTAGS:
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(PomodoroTimerDBContract.PomodorosTagsTable.TABLE_NAME,
                            values, selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}