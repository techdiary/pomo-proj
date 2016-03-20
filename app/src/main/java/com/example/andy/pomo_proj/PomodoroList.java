package com.example.andy.pomo_proj;

/**
 * Created by Andy on 3/18/2016.
 */
import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.LoaderManager;
import android.content.Loader;
import android.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * The Class PomodoroList. Shows list of all pomodoros from databases.
 */
public class PomodoroList extends Activity implements OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor>
{

    /** The Constant NO_FLAGS. */
    private static final int NO_FLAGS = 0;

    /** The pomodoro list. */
    private ListView pomodoroListLV;

    /** The adapter. */
    private SimpleCursorAdapter adapter;

    /** The projection. */
    String[] projection = new String[] {
            PomodoroTimerDBContract.PomodorosTable.COLUMN_NAME_START_DATE,
            PomodoroTimerDBContract.PomodorosTable.COLUMN_NAME_STOP_DATE,
            PomodoroTimerDBContract.PomodorosTable.COLUMN_NAME_START_HOUR,
            PomodoroTimerDBContract.PomodorosTable.COLUMN_NAME_STOP_HOUR,
            PomodoroTimerDBContract.COLUMN_ID
    };

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro_list);

        pomodoroListLV = (ListView) findViewById(R.id.pomodoroList);

        pomodoroListLV.setOnItemClickListener(this);

        getLoaderManager().initLoader(1, null, this);
    }

    /* (non-Javadoc)
     * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id)
    {
        Intent intent = new Intent(this, PomodoroDetails.class);
        intent.putExtra("dbID", id);
        startActivity(intent);
    }

    /* (non-Javadoc)
     * @see android.app.LoaderManager.LoaderCallbacks#onCreateLoader(int, android.os.Bundle)
     */
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1)
    {
        CursorLoader cursorLoader = new CursorLoader(this, PomodoroContentProvider.CONTENT_URI_POMODOROS, projection, null, null, PomodoroTimerDBContract.PomodorosTable._ID + " ASC");
        return cursorLoader;
    }

    /* (non-Javadoc)
     * @see android.app.LoaderManager.LoaderCallbacks#onLoadFinished(android.content.Loader, java.lang.Object)
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        if(adapter == null)
        {
            //TODO: rebuild listlayout.xml - it's ugly
            adapter = new SimpleCursorAdapter(this, R.layout.pomodoro_list_layout, data, projection, new int[] { R.id.TextView1, R.id.TextView3, R.id.TextView2, R.id.TextView4}, NO_FLAGS);
        }
        else
        {
            adapter.swapCursor(data);
        }
        pomodoroListLV.setAdapter(adapter);
    }

    /* (non-Javadoc)
     * @see android.app.LoaderManager.LoaderCallbacks#onLoaderReset(android.content.Loader)
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        if(adapter != null)
        {
            adapter.swapCursor(null);
        }

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        getLoaderManager().restartLoader(1, null, this);

    }

    //TODO: if list is empty show any statement


}
