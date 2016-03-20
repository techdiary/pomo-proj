package com.example.andy.pomo_proj;

/**
 * Created by Andy on 3/18/2016.
 */
/**
 * The Class TagClass.
 * Used for operations on objects. Helps with database operations.
 * Stores information about tag.
 */
public class TagClass
{

    /**
     * Instantiates a new tag class.
     */
    public TagClass()
    {
        super();
    }

    /**
     * Instantiates a new tag class completing it's fields.
     *
     * @param id the tag's id
     * @param _tagName the tag's name
     * @param _lastUsed the tag's last used date
     */
    public TagClass(long id, String _tagName, String _lastUsed)
    {
        _id = id;
        tagName = _tagName;
        lastUsed = _lastUsed;
    }

    /** The tag's _id. */
    private long _id;

    /** The tag's name. */
    private String tagName;

    /** The date of tag's last use. */
    private String lastUsed;

    /**
     * Gets the tag's name.
     *
     * @return the tag's name
     */
    public String getTagName()
    {
        return tagName;
    }

    /**
     * Sets the tag's name.
     *
     * @param tagName the new tag's name
     */
    public void setTagName(String tagName)
    {
        this.tagName = tagName;
    }

    /**
     * Gets the date of tag's last use.
     *
     * @return the date of tag's last use
     */
    public String getLastUsed()
    {
        return lastUsed;
    }

    /**
     * Sets the date of tag's last use.
     *
     * @param lastUsed the new date of tag's last use
     */
    public void setLastUsed(String lastUsed)
    {
        this.lastUsed = lastUsed;
    }

    /**
     * Gets the tag's id.
     *
     * @return the tag's id
     */
    public long getID()
    {
        return _id;
    }

    /**
     * Sets the tag's id.
     *
     * @param _id the tag's new id
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
        return tagName;
    }
}