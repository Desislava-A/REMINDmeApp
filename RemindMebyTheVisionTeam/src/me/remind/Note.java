package me.remind;

public abstract class Note
{

    public static final int MIN_TITLE_LENGTH = 3;
    public static final int MAX_TITLE_LENGTH = 60;
    public static final int DEADLINE_LENGTH = 10;
    public Note(String title, String deadline) {
        this.title = title;
        this.deadline = deadline;
    }
    private String title;
    private String deadline;
    private Priority priority;
    
    public Note(String title, String deadline, Priority priority)
    {
        setTitle(title);
        setDeadline(deadline);
        setPriority(Priority.NONE);
    }

    

    private void setTitle(String title)
    {
        if (title == null)
            return;
        
        this.title = title;
    }
    
    private void setDeadline(String deadline)
    {
        if (title == null)
            return;
        
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
    
    @Override
    public String toString()
    {
        return String.format("[ %s ] until: %s", title, deadline);
    }
    
    public abstract void makeNote();
    
    public abstract void reviewNote();
}
