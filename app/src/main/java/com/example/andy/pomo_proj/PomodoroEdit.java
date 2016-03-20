package com.example.andy.pomo_proj;

/**
 * Created by Andy on 3/18/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * The Class PomodoroEdit.
 */
public class PomodoroEdit extends Activity implements OnClickListener
{
    /** The db id. */
    private long dbID;

    /** The title. */
    private TextView title;

    /** The save. */
    private Button save;

    /** The cancel. */
    private Button cancel;

    /** The startdate. */
    private EditText startdate;

    /** The starthour. */
    private EditText starthour;

    /** The stopdate. */
    private EditText stopdate;

    /** The stophour. */
    private EditText stophour;

    /** The numInRow. */
    private EditText numinrow;

    /** The db. */
    private PomodoroTimerDBHelper db;

    /**  The tag. */
    private PomodoroClass pomodoro;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro_edit);

        db = new PomodoroTimerDBHelper(getApplicationContext());
        Intent intent = getIntent();
        dbID  = intent.getLongExtra("dbID", 0);

        pomodoro = db.getPomodoro(dbID);

        title = (TextView) findViewById(R.id.editPomodoroTitle);
        startdate = (EditText) findViewById(R.id.editPomodoroStartDate);
        starthour = (EditText) findViewById(R.id.editPomodoroStartHour);
        stopdate = (EditText) findViewById(R.id.editPomodoroStopDate);
        stophour = (EditText) findViewById(R.id.editPomodoroStopHour);
        numinrow = (EditText) findViewById(R.id.editPomodoroNumInRow);

        title.setText("Pomodoro no " + dbID);
        startdate.setText(pomodoro.getStartDate());
        starthour.setText(pomodoro.getStartHour());
        stopdate.setText(pomodoro.getStopDate());
        stophour.setText(pomodoro.getStopHour());
        numinrow.setText(String.valueOf(pomodoro.getNumInRow()));

        save = (Button) findViewById(R.id.editSave);
        cancel = (Button) findViewById(R.id.editCancel);

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.editSave:
                pomodoro.setStartDate(startdate.getText().toString());
                pomodoro.setStartHour(starthour.getText().toString());
                pomodoro.setStopDate(stopdate.getText().toString());
                pomodoro.setStopHour(stophour.getText().toString());
                pomodoro.setNumInRow(Integer.parseInt(numinrow.getText().toString()));
                db.updatePomodoro(pomodoro);
                finish();
                break;
            case R.id.editCancel:
                finish();
                break;
        }

    }
}
