package com.example.andy.pomo_proj;

/**
 * Created by Andy on 3/18/2016.
 */
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

/**
 * The Class About. Shows information about application (e.g. author, name) in application's menu.
 */
public class About extends Activity
{
    /** The title. */
    private String title;

    /** The author title. */
    private String authorTitle;

    /** The author. */
    private String author;

    /** The compilation date title. */
    private String compilationDateTitle;

    /** The compilation date. */
    private String compilationDate;

    /** The title tv. */
    private TextView titleTV;

    /** The author title tv. */
    private TextView authorTitleTV;

    /** The author tv. */
    private TextView authorTV;

    /** The compilation date tv. */
    private TextView compilationDateTV;

    /** The compilation date title tv. */
    private TextView compilationDateTitleTV;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Calendar cal = Calendar.getInstance();
        int compilationDay = cal.get(Calendar.DAY_OF_MONTH);
        int compilationMonth = cal.get(Calendar.MONTH) + 1;
        int compilationYear = cal.get(Calendar.YEAR);

        title = "Pomodoro Timer";
        authorTitle = "Author:";
        author = "Aayush Sharma";
        compilationDateTitle = "Compilation date";
        compilationDate = String.format("%02d.%02d.%04d", compilationDay, compilationMonth, compilationYear);

        titleTV = (TextView) findViewById(R.id.appTitle);
        titleTV.setText(title);
        authorTitleTV = (TextView) findViewById(R.id.appAuthorTitle);
        authorTitleTV.setText(authorTitle);
        authorTV = (TextView) findViewById(R.id.appAuthor);
        authorTV.setText(author);
        compilationDateTitleTV = (TextView) findViewById(R.id.appCompilationDateTitle);
        compilationDateTitleTV.setText(compilationDateTitle);
        compilationDateTV = (TextView) findViewById(R.id.appCompilatioDate);
        compilationDateTV.setText(compilationDate);

    }
}
