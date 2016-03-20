package com.example.andy.pomo_proj;

/**
 * Created by Andy on 3/18/2016.
 */
/**
 * The Class PomodorosTagsClass.
 * Used for operations on objects. Helps with database operations.
 * Stores information about pomodoro-tags connection.
 */
public class PomodorosTagClass
{

    /**
     * Instantiates a new pomodoros' tags class.
     */
    public PomodorosTagClass()
    {
        super();
    }

    /**
     * Instantiates a new pomodoros tag class completing it's fields.
     *
     * @param id the pomodoro tag's id
     * @param _pomodoroID the pomodoro's id
     * @param _tagID the tag's id
     */
    public PomodorosTagClass(long id, long _pomodoroID, long _tagID)
    {
        _id = id;
        pomodoroID = _pomodoroID;
        tagID = _tagID;
    }

    /** The object's _id. */
    private long _id;

    /** The pomodoro's id. */
    private long pomodoroID;

    /** The tag's id. */
    private long tagID;

    /**
     * Gets the pomodoro's id.
     *
     * @return the pomodoro's id
     */
    public long getPomodoroID()
    {
        return pomodoroID;
    }

    /**
     * Sets the pomodoro's id.
     *
     * @param pomodoroID the new pomodoro's id
     */
    public void setPomodoroID(int pomodoroID)
    {
        this.pomodoroID = pomodoroID;
    }

    /**
     * Gets the tag's id.
     *
     * @return the tag's id
     */
    public long getTagID()
    {
        return tagID;
    }

    /**
     * Sets the tag's id.
     *
     * @param tagID the new tag's id
     */
    public void setTagID(int tagID)
    {
        this.tagID = tagID;
    }

    /**
     * Gets the object's id.
     *
     * @return the id
     */
    public long getID()
    {
        return _id;
    }

    /**
     * Sets the object's id.
     *
     * @param _id the new id
     */
    public void setID(int _id)
    {
        this._id = _id;
    }


}