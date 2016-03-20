package com.example.andy.pomo_proj;

/**
 * Created by Andy on 3/18/2016.
 */
/**
 * The Class PomodoroClass.
 * Used for operations on objects. Helps with database operations.
 * Stores information about pomodoro.
 */
public class PomodoroClass
{

    /**
     * Instantiates a new pomodoro class.
     */
    public PomodoroClass()
    {
        super();
    }

    /**
     * Instantiates a new pomodoro class completing it's fields.
     *
     * @param dbID the id
     * @param _startDate the pomodoro's start date
     * @param _stopDate the pomodoro's stop date
     * @param _startHour the pomodoro's start hour
     * @param _stopHour the pomodoro's stop hour
     * @param _numInRow the pomodoro's num in row
     */
    public PomodoroClass(long dbID, String _startDate, String _stopDate, String _startHour, String _stopHour, int _numInRow )
    {
        _id = dbID;
        startDate = _startDate;
        stopDate = _stopDate;
        startHour = _startHour;
        stopHour = _stopHour;
        numInRow = _numInRow;
    }

    /** The pomodoro's _id. */
    private long _id;

    /** The pomodoro's start date. */
    private String startDate;

    /** The pomodoro's start hour. */
    private String startHour;

    /** The pomodoro's stop date. */
    private String stopDate;

    /** The pomodoro's stop hour. */
    private String stopHour;

    /** The pomodoro's number in a row. */
    private int numInRow;

    /**
     * Gets the pomodoro's start date.
     *
     * @return the pomodoro's start date
     */
    public String getStartDate()
    {
        return startDate;
    }

    /**
     * Sets the pomodoro's start date.
     *
     * @param startDate the new pomodoro's start date
     */
    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    /**
     * Gets the pomodoro's start hour.
     *
     * @return the pomodoro's start hour
     */
    public String getStartHour()
    {
        return startHour;
    }

    /**
     * Sets the pomodoro's start hour.
     *
     * @param startHour the new pomodoro's start hour
     */
    public void setStartHour(String startHour)
    {
        this.startHour = startHour;
    }

    /**
     * Gets the pomodoro's stop date.
     *
     * @return the pomodoro's stop date
     */
    public String getStopDate()
    {
        return stopDate;
    }

    /**
     * Sets the pomodoro's stop date.
     *
     * @param stopDate the new pomodoro's stop date
     */
    public void setStopDate(String stopDate)
    {
        this.stopDate = stopDate;
    }

    /**
     * Gets the pomodoro's stop hour.
     *
     * @return the pomodoro's stop hour
     */
    public String getStopHour()
    {
        return stopHour;
    }

    /**
     * Sets the pomodoro's stop hour.
     *
     * @param stopHour the newpomodoro's stop hour
     */
    public void setStopHour(String stopHour)
    {
        this.stopHour = stopHour;
    }

    /**
     * Gets the pomodoro's number in a row.
     *
     * @return the pomodoro's number in a row
     */
    public int getNumInRow()
    {
        return numInRow;
    }

    /**
     * Sets the pomodoro's number in a row.
     *
     * @param numInRow the new pomodoro's number in a row
     */
    public void setNumInRow(int numInRow)
    {
        this.numInRow = numInRow;
    }

    /**
     * Gets the pomodoro's id.
     *
     * @return the id
     */
    public long getID()
    {
        return _id;
    }

    /**
     * Sets the pomodoro's id.
     *
     * @param _id the new id
     */
    public void setID(int _id)
    {
        this._id = _id;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        String str = "Pomodoro " + _id + " (" + startDate + " " + startHour + " - " + stopDate + " " + stopHour + ")";
        return str;
    }
}
