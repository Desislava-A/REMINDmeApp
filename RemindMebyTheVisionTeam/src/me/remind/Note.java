package me.remind;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public abstract class Note
{
    public static final int MIN_TITLE_LENGTH = 3;
    public static final int MAX_TITLE_LENGTH = 60;
    public static final int DEADLINE_LENGTH = 10;
    
    private String title;
    private Calendar deadline;
    protected static int noteCounter = 1;
    
    private Priority priority;
    
    public Note()
    {
    }
    
    public Note(String title)
    {
        this(title, null);
    }
    
    public Note(String title, Calendar deadline)
    {
        this(title, deadline, Priority.NONE);
    }
    
    public Note(String title, Calendar deadline, Priority priority)
    {
        setTitle(title);
        setDeadline(deadline);
        setPriority(priority);
    }
    
    public String getTitle()
    {
        return title;
    }
    
    private void setTitle(String title)
    {
        if (title == null)
            return;
        
        this.title = title;
    }
    
    public Calendar getDeadline()
    {
        return deadline;
    }
    
    private void setDeadline(Calendar deadline)
    {
        this.deadline = deadline;
    }
    
    private void setPriority(Priority priority)
    {
        this.priority = priority;
    }
    
    public void changePriority(Priority priority)
    {
        if (this.priority != priority)
            this.priority = priority;
    }
    
    protected long getDaysToDeadline()
    {
        return TimeUnit.MILLISECONDS.toDays(deadline.getTimeInMillis() -
                System.currentTimeMillis()) + 1;
    }
    
    protected void printTitle()
    {
        System.out.println(getTitle());
    }
    
    public abstract void showNote();
}
