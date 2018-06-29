package me.remind;

public abstract class Note
{
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
    
    public abstract void search();
}
