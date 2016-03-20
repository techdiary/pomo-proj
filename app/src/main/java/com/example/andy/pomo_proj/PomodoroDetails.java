package com.example.andy.pomo_proj;

/**
 * Created by Andy on 3/18/2016.
 */
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * The Class PomodoroDetails. Shows details of the pomodoro from the list.
 */
public class PomodoroDetails extends Activity implements OnClickListener
{


    /** The db id. */
    private long dbID;

    /** The title. */
    private TextView title;

    /** The start. */
    private TextView start;

    /** The stop. */
    private TextView stop;

    /** The num. */
    private TextView num;

    /** The list. */
    private ListView list;

    /** The edit. */
    private Button edit;

    /** The remove. */
    private Button remove;

    /** The db. */
    private PomodoroTimerDBHelper db;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro_details);
        db = new PomodoroTimerDBHelper(getApplicationContext());
        Intent intent = getIntent();
        dbID  = intent.getLongExtra("dbID", 0);

        title = (TextView) findViewById(R.id.detailsTitle);
        start = (TextView) findViewById(R.id.detailsStartDateHour);
        stop = (TextView) findViewById(R.id.detailsStopDateHour);
        num = (TextView) findViewById(R.id.detailsNumInRow);
        list = (ListView) findViewById(R.id.taglist);
        edit = (Button) findViewById(R.id.detailsEdit);
        remove = (Button) findViewById(R.id.detailsRemove);

        edit.setOnClickListener(this);
        remove.setOnClickListener(this);

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        showPomodoro();
    }

    /**
     * Show pomodoro selected by id.
     */
    private void showPomodoro()
    {
        PomodoroClass pomodoro = db.getPomodoro(dbID);
        TagClass tag;
        long tmp;

        title.setText("Pomodoro nr " + dbID);
        start.setText("Rozpocz?to: " + pomodoro.getStartDate() + " o godzinie " + pomodoro.getStartHour() + ".");
        stop.setText("Zako?czono: " + pomodoro.getStopDate() + " o godzinie " + pomodoro.getStopHour() + ".");
        num.setText("Jest to " + pomodoro.getNumInRow() + ". pomodoro z rz?du.");

        List<TagClass> tags = db.getPomodorosTags(dbID);
        //Log.d("DEBUG", "ID: " + String.valueOf(dbID));
        int len = tags.toArray().length;
        //Log.d("DEBUG", "Len: " + String.valueOf(len));

        ArrayList<String> strList = new ArrayList<String>();
        String tagname;

//    for (int i = 0; i < len; i++)
//    {
//      Log.d("taglist", String.valueOf(tags.get(i).getID()));
//    }


        for (int i = 0; i < len; i++)
        {
            tmp = tags.get(i).getID();
            //Log.d("DEBUG", "tagid: " + String.valueOf(tmp));
            tag = db.getTag(tmp);
            tagname = tag.getTagName();
            //Log.d("DEBUG", "Tagname: " + tagname);
            strList.add(tagname);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strList);
        list.setAdapter(adapter);

    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.detailsEdit:
                Intent intent = new Intent(this, PomodoroEdit.class);
                intent.putExtra("dbID", dbID);
                startActivity(intent);
                break;
            case R.id.detailsRemove:
                db.deletePomodoro(dbID);  //TODO: add confirmation dialog
                finish();
                break;

        }

    }
}

