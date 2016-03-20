package com.example.andy.pomo_proj;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, SensorEventListener {

    /** The pauseTime variable. Used for resuming timer. */
    private long pauseTime;

    /** The counting variable. Used for detect ANY counting. */
    private boolean counting;

    /** The up variable. Used for detect position. */
    private boolean up;

    /** The ifTimerPaused flag. Used for checking if timer is stopped or paused. */
    private boolean ifTimerPaused;

    /** The ifPomodoroLasts flag. Used for checking if counter counts pomodoro or break. */
    private boolean ifPomodoroLasts;

    /** The pomodoro's duration. Saved in SharedPreferences. */
    private long pomodoroDuration;

    /** The short break duration. Saved in SharedPreferences. */
    private long shortBreakDuration;

    /** The long break duration. Saved in SharedPreferences. */
    private long longBreakDuration;

    /** The long break interval. Saved in SharedPreferences. */
    private int longBreakInterval;

    /** The interval variable. Used to count pomodoros between long breaks. */
    private int interval;

    /** The useSound variable. Used for check if app should use sound alarms.  */
    private boolean useSound;

    /** The ringtone variable. Stores ringtone for sound alarm. */
    private Ringtone ringtone;

    /** The CountDownTimer variable. Counts pomodoro or break. */
    private static CountDownTimer cdTimer;

    /** The start button. Starts/resumes pomodoro or resumes break. */
    private Button startButton;

    /** The stop button. Stops pomodoro or break. Resets all variables (countDownTimer, labels etc.) to default values. */
    private Button stopButton;

    /** The pause button. Pauses pomodoro or break. */
    private Button pauseButton;

    /** The tvTimer. Shows counting timer. */
    private TextView tvTimer;

    /** The countingState label showing state of timer (counting pomodoro/break, paused etc.). */
    private TextView countingState;

    /** The database variable. Used for writing/reading data from database. */
    private PomodoroTimerDBHelper db;

    /** The date of pomodoro's start. */
    private String startDate;

    /** The date of pomodoro's stop. */
    private String stopDate;

    /** The hour of pomodoro's start. */
    private String startHour;

    /** The hour of pomodoro's stop. */
    private String stopHour;

    /** The log's tag. */
    private String TAG_POMODORO;

    /** The log's tag. */
    private String TAG_MISC;

    /** The log's tag. */
    private String TAG_PREFS;

    /** The log's tag. */
    private String TAG_SENSOR;

    private SensorManager sensorManager;

    private Sensor sAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TAG_POMODORO = "POMODORO";
        TAG_MISC = "MISCELLANOUS";
        TAG_PREFS = "PREFRENCES";
        TAG_SENSOR = "SENSOR";

        ifTimerPaused = false;
        counting = false;
        pauseTime = 0;
        ifPomodoroLasts = true;
        up = true;
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        tvTimer = (TextView) findViewById(R.id.timer);
        countingState = (TextView) findViewById(R.id.countingState);
        db = new PomodoroTimerDBHelper(getApplicationContext());
        cdTimer = null;

        stopButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);

        countingState.setText(R.string.countingStateNothing);
        Log.i(TAG_MISC, "Application started");

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    protected  void onResume() {
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences((this));
        pomodoroDuration = Long.parseLong(sharedPrefs.getString("pmodoroDuration", "1500000"));
        shortBreakDuration = Long.parseLong(sharedPrefs.getString("shortBreakDuration", "300000"));
        longBreakDuration = Long.parseLong(sharedPrefs.getString("longBreakDuration", "900000"));
        longBreakInterval = Integer.parseInt(sharedPrefs.getString("longBreakInterval", "4"));
        interval = 0;

        Log.i(TAG_PREFS, "Pomodoro's prefrences sets.");

        useSound = sharedPrefs.getBoolean("useSound", true);
        if (useSound) {
            String rt = sharedPrefs.getString("alarmRingtone", null);
            Uri rtUri;
            if (rt == null) {
                rtUri = RingtoneManager.getDefaultUri((RingtoneManager.TYPE_NOTIFICATION));
            } else {
                rtUri = Uri.parse(rt);
            }
            ringtone = RingtoneManager.getRingtone(MainActivity.this, rtUri);
        } else {
            ringtone = null;
        }
        Log.i(TAG_PREFS, "Alarm Prefrences Sets.");

        if (pauseTime > 900) {
            ifTimerPaused = true;
            countingState.setText(R.string.countingStatePause);
            if (pauseButton.getVisibility() == View.VISIBLE) {
                pauseButton.setVisibility((View.INVISIBLE));
                startButton.setVisibility(View.VISIBLE);
            }
        } else {
            countingState.setText((R.string.countingStateNothing));
            ifTimerPaused = false;
            pauseButton.setVisibility(View.INVISIBLE);
            startButton.setVisibility(View.VISIBLE);
        }
        Log.i(TAG_POMODORO, "Pomodoro ready to start/resume.");

        sensorManager.registerListener(this, sAcc, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(TAG_SENSOR, "Sensor registerd.");

    }/*Implementation of onPause() method.
        * CAncel countDownTimer.
        * Unregister Sensor.
      */
    @Override
    protected  void  onPause()
    {
        super.onPause();
        if(cdTimer != null)
        {
            cdTimer.cancel();
            counting = false;
            Log.i(TAG_POMODORO,"Pomodoro paused/stopped");
        }
        sensorManager.unregisterListener(this);
        Log.i(TAG_SENSOR,"Sensor unregistered");
    }
    /*onCreateOptionMenu(android.view.Menu)

     */
/*
    OnCLickListner#onCLick(android.view.View)
   *Implementation of onClick() method.
   * Supports all activity buttons:
   * startButton:
   * - starts pomodoro or break,
   * - saves info of pomodoro for database,
   * stopButton:
   * - stops pomodoro or break immediately,
   * - doesn't save pomodoro info to database,
   *   pauseButton:
   * - pauses pomodoro or break.
 */
    @Override
    public void onClick(View v)
    {
        switch(v.getId()) {
            case R.id.startButton:
                counting = true;
                if (ifPomodoroLasts) {
                    if (ifTimerPaused) {
                        startPomodoro(pauseTime);
                    } else {
                        startPomodoro(pomodoroDuration);
                    }
                } else {
                    startBreak(pauseTime);
                }
                startButton.setVisibility(View.INVISIBLE);
                pauseButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.VISIBLE);
                break;
            case R.id.stopButton:
                cdTimer.cancel();
                counting = false;
                ifTimerPaused = false;

                if (pauseButton.getVisibility() == View.VISIBLE) {
                    pauseButton.setVisibility(View.INVISIBLE);
                    startButton.setVisibility(View.VISIBLE);

                }
                countingState.setText((R.string.countingStateNothing));
                tvTimer.setText(String.format("%02d:%02d", 0, 0));
                ifPomodoroLasts = true;

                Log.i(TAG_POMODORO, "Pomodoro/break stopped.");
                break;
            case R.id.pauseButton:
                cdTimer.cancel();
                counting = false;
                ifTimerPaused = true;

                startButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.INVISIBLE);
                countingState.setText((R.string.countingStatePause));
                Log.i(TAG_POMODORO, "Pomodoro/break paused.");
                break;
        }
    }

    /* @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
    */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.pomodoro_list:
                startActivity(new Intent(this,PomodoroList.class));
                return true;
            case R.id.tag_list:
                startActivity(new Intent(this, TagList.class));
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this,SettingsActivity.class));
                return true;
            case R.id.about:
                startActivity(new Intent(this, About.class));
                return true;
            case R.id.exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /*
        *Starts pomodoro.
        * Sets params of countDownTimer with appropriate numbers.
        * Sets labels for pomodoro counting.
        * @param duration the duration of the pomodoro
     */
    void startPomodoro(long duration)
    {
        Calendar cal = Calendar.getInstance();
        startDate = String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
        startHour = String.format("%02d:%02d;%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));

        Log.d("startDate",startDate);
        Log.d("startHour",startHour);

        ifPomodoroLasts = true;
        counting =true;
        countingState.setText(R.string.countingStatePomodoro);
        cdTimer = new CountDownTimer(duration,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) millisUntilFinished/1000;
                int minutes = seconds /60;
                seconds = seconds % 60;

                tvTimer.setText(String.format("%02d:%02d", minutes, seconds));
                pauseTime = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                cdFinish();
                if(interval % longBreakInterval == 0)
                {
                    startBreak(longBreakDuration);

                }
                else {
                    startBreak(shortBreakDuration);
                }

            }
        };

        cdTimer.start();
        Log.i(TAG_POMODORO, "Pomdoro started/resumed.");
    }
/**
 * Starts break.
 * Sets params of countdowntimer with appropriate numbers.
 * Sets labels for break.
 */
void startBreak(long duration)
{
    ifPomodoroLasts = false;
    counting = true;
    countingState.setText(R.string.countingStateBreak);
    cdTimer = new CountDownTimer(duration,1000)
    {
        @Override
        public void onTick(long millisUntilFinished)
        {
            int seconds = (int) millisUntilFinished/1000;
            int minutes = seconds / 60;
            seconds = seconds % 60;

            tvTimer.setText(String.format("%02d:%02d", minutes, seconds));
            pauseTime = millisUntilFinished;
        }

        @Override
        public void onFinish()
        {
            startPomodoro(pomodoroDuration);
            if(ringtone != null)
            {
                ringtone.play();
            }
        }
    };

    cdTimer.start();
    Log.i(TAG_POMODORO, "Break started/resumed.");
}

    /**
     * Method used when CoundDownTimer of the pomodoro finishes.
     * Adds info about pomodoro to database.
     * Increments 'interval' variable.
     */
    void cdFinish() {
        if (ringtone != null) {
            ringtone.play();
        }

        Calendar cal = Calendar.getInstance();
        stopDate = String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        stopHour = String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));

        Log.i(TAG_POMODORO, "Pomodoro finished.");
        Log.d("stopDate", stopDate);
        Log.d("stopHour", stopHour);

        ifTimerPaused = false;
        counting = false;
        interval++;
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Set other dialog properties
        builder.setTitle("Select Tags:");

        final List<TagClass> lastUsedTags = db.getLastUsedTags(-1);
        int len = lastUsedTags.toArray().length;

        final boolean[] itemsChecked = new boolean[len];
        List<CharSequence> tagsNames = new ArrayList<CharSequence>();

        for (int i = 0; i < len; i++) {
            tagsNames.add(lastUsedTags.get(i).getTagName());
        }

        builder.setMultiChoiceItems(tagsNames.toArray(new CharSequence[tagsNames.size()]), null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        itemsChecked[which] = isChecked;
                    }
                });

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                int numInRow = interval % longBreakInterval;
                if (numInRow == 0) {
                    numInRow = longBreakInterval;
                }
                PomodoroClass pomodoro = new PomodoroClass(0, startDate, stopDate, startHour, stopHour, numInRow);
                int id_ = (int) db.addPomodoro(pomodoro);
                pomodoro.setID(id_);
                Log.i(TAG_POMODORO, "Pomodoro added to database.");

                addTags(lastUsedTags, itemsChecked, id_);
                Log.i(TAG_POMODORO, "Pomodoro's tags added to database.");

            }
        });
        builder.setNegativeButton("Del", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                Log.i(TAG_POMODORO, "Pomodoro not added to database.");
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    void addTags(List<TagClass> tags, boolean[] selected, int pomodoroID)
    {
        int len = tags.toArray().length;
        PomodorosTagClass pomodorostag;
        TagClass tag;
        long id;

        for(int i = 0; i < len; i++)
        {
            if(selected[i])
            {
                tag = tags.get(i);
                id = tag.getID();
                pomodorostag = new PomodorosTagClass(0, pomodoroID, id);
                db.addPomodorosTag(pomodorostag);

                String lastUsed = stopDate + " " + stopHour;
                tag.setLastUsed(lastUsed);
                db.updateTag(tag);
            }
        }
    }
    @Override
    public void onSensorChanged(SensorEvent event)
    {

        int zNew = 0, zOld;
        zOld = zNew;
        zNew = (int) event.values[2];

        int delta = Math.abs(zNew) - Math.abs(zOld);

        if(delta >= 1)
        {
            if(zNew >= 9 && zNew <= 11)
            {
                if(counting && !up)
                {
                    cdTimer.cancel();
                    counting = false;
                    ifTimerPaused = true;

                    startButton.setVisibility(View.VISIBLE);
                    pauseButton.setVisibility(View.INVISIBLE);
                    countingState.setText(R.string.countingStatePause);
                    Log.i(TAG_POMODORO, "Pomodoro/break paused.");

                    startButton.setVisibility(View.VISIBLE);
                    pauseButton.setVisibility(View.INVISIBLE);

                    up = true;
                }
                //up
                //up - pause
                //tvTimer.setText("UP");
            }
            else if (zNew >= -10 && zNew <= -8)
            {
                if(!counting && !ifTimerPaused && up)
                {
                    startButton.setVisibility(View.INVISIBLE);
                    pauseButton.setVisibility(View.VISIBLE);
                    startPomodoro(pomodoroDuration);
                }
                if(!counting && ifTimerPaused && up)
                {
                    startButton.setVisibility(View.INVISIBLE);
                    pauseButton.setVisibility(View.VISIBLE);
                    startPomodoro(pauseTime);
                }

                up = false;
                //down - start
                //tvTimer.setText("DOWN");
            }

        }


    }



        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
