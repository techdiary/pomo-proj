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

public class TagEdit extends Activity implements OnClickListener
{
    /** The db id. */
    private long dbID;

    /** The title. */
    private TextView title;

    /** The tag's date. */
    private TextView tagname;

    /** The save. */
    private Button save;

    /** The cancel. */
    private Button cancel;

    /** The db. */
    private PomodoroTimerDBHelper db;

    /** The tag */
    private TagClass tag;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_edit);


        db = new PomodoroTimerDBHelper(getApplicationContext());
        Intent intent = getIntent();
        dbID  = intent.getLongExtra("dbID", -1);

        title = (TextView) findViewById(R.id.editTitle);
        tagname = (EditText) findViewById(R.id.editTagName);

        save = (Button) findViewById(R.id.editSave);
        cancel = (Button) findViewById(R.id.editCancel);

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);

        if(dbID == -1)
        {
            title.setText("Wpisz nazw? taga:");
            tagname.setText("");
            tag = new TagClass(0, "", "0000-00-00 00:00:00");
        }
        else
        {
            tag = db.getTag(dbID);
            title.setText("Tag nr " + dbID);
            tagname.setText(tag.getTagName());
        }
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.editSave:
                tag.setTagName(tagname.getText().toString());
                if(dbID == -1)
                {
                    db.addTag(tag, db.getWritableDatabase());
                }
                else
                {
                    db.updateTag(tag);
                }
                finish();
                break;
            case R.id.editCancel:
                finish();
                break;
        }

    }
}
