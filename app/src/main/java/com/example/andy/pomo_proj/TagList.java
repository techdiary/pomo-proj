package com.example.andy.pomo_proj;

/**
 * Created by Andy on 3/18/2016.
 */
import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class TagList extends Activity implements LoaderManager.LoaderCallbacks<Cursor>, OnItemClickListener
{
    /** The Constant NO_FLAGS. */
    private static final int NO_FLAGS = 0;

    /** The tag list. */
    private ListView tagListLV;

    /** The adapter. */
    private SimpleCursorAdapter adapter;

    /** The projection. */
    String[] projection = new String[] {
            PomodoroTimerDBContract.TagsTable.COLUMN_NAME_TAG_NAME,
            PomodoroTimerDBContract.TagsTable.COLUMN_NAME_LAST_USED,
            PomodoroTimerDBContract.COLUMN_ID
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_list);

        tagListLV = (ListView) findViewById(R.id.tagList);
        tagListLV.setOnItemClickListener(this);
        getLoaderManager().initLoader(1, null, this);
    }

    /* (non-Javadoc)
     * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id)
    {
        Intent intent = new Intent(this, TagDetails.class);
        intent.putExtra("dbID", id);
        startActivity(intent);
    }

    /* (non-Javadoc)
     * @see android.app.LoaderManager.LoaderCallbacks#onCreateLoader(int, android.os.Bundle)
     */
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1)
    {
        CursorLoader cursorLoader = new CursorLoader(this, PomodoroContentProvider.CONTENT_URI_TAGS, projection, null, null, PomodoroTimerDBContract.TagsTable._ID + " ASC");
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
            adapter = new SimpleCursorAdapter(this, R.layout.tag_list_layout, data, projection, new int[] { R.id.TextView1, R.id.TextView2}, NO_FLAGS);
        }
        else
        {
            adapter.swapCursor(data);
        }
        tagListLV.setAdapter(adapter);
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

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addtag, menu);
        return true;
    }
    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.addTag:
                Intent intent = new Intent(this, TagEdit.class);
                intent.putExtra("dbID", -1);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}