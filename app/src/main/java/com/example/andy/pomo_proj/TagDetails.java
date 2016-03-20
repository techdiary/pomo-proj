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
import android.widget.TextView;

public class TagDetails extends Activity implements OnClickListener
{

    /** The db id. */
    private long dbID;

    /** The title. */
    private TextView title;

    /** The tag's date. */
    private TextView tagname;

    /** The last used date. */
    private TextView lastUsed;

    /** The edit. */
    private Button edit;

    /** The remove. */
    private Button remove;

    /** The db. */
    private PomodoroTimerDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_details);

        db = new PomodoroTimerDBHelper(getApplicationContext());
        Intent intent = getIntent();
        dbID  = intent.getLongExtra("dbID", 0);

        title = (TextView) findViewById(R.id.detailsTitle);
        tagname = (TextView) findViewById(R.id.detailsName);
        lastUsed = (TextView) findViewById(R.id.detailsLastUsed);
        edit = (Button) findViewById(R.id.detailsEdit);
        remove = (Button) findViewById(R.id.detailsRemove);

        edit.setOnClickListener(this);
        remove.setOnClickListener(this);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        showTag();
    }


    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.detailsEdit:
                Intent intent = new Intent(this, TagEdit.class);
                intent.putExtra("dbID", dbID);
                startActivity(intent);
                break;
            case R.id.detailsRemove:
                db.deleteTag(dbID);  //TODO: add confirmation dialog
                finish();
                break;
        }

    }

    /**
     * Show tag selected by id.
     */
    private void showTag()
    {
        TagClass tag = db.getTag(dbID);

        title.setText("Tag nr " + dbID);
        tagname.setText("Nazwa: " + tag.getTagName());
        lastUsed.setText("Data ostatniego u?ycia: " + tag.getLastUsed());

    }
}