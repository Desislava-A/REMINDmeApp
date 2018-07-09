package me.remind;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

public abstract class Note implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private static final int MIN_TITLE_LENGTH = 3;
    private static final int MAX_TITLE_LENGTH = 60;
    
    private UUID uid;
    private String title;
    private Priority priority;
    private boolean pinned;
    private boolean archived;
    
    public Note()
    {
    }
    
    public Note(String title, Priority priority)
    {
        setUid(UUID.randomUUID());
        setTitle(title);
        setPriority(priority);
        setPinned(false);
        setArchived(false);
    }
    
    protected String getUid()
    {
        return uid.toString();
    }
    
    private void setUid(UUID uid)
    {
        this.uid = uid;
    }
    
    protected String getTitle()
    {
        return title;
    }
    
    protected void setTitle(String title)
    {
        if (title == null)
            return;
        
        if (title.length() < MIN_TITLE_LENGTH || title.length() > MAX_TITLE_LENGTH)
            return;
            
        this.title = title;
    }
    
    protected Priority getPriority()
    {
        return priority;
    }
    
    protected void setPriority(Priority priority)
    {
        this.priority = priority;
    }
    
    protected boolean isPinned()
    {
        return pinned;
    }
    
    protected void setPinned(boolean pinned)
    {
        this.pinned = pinned;
    }
    
    protected boolean isArchived()
    {
        return archived;
    }
    
    protected void setArchived(boolean archived)
    {
        this.archived = archived;
    }
    
    protected void printTitle()
    {
        System.out.println(getTitle());
    }
    
    protected abstract void showNote() throws IOException;
    
    protected abstract String getTitleWithTypeAndPriority();
}
