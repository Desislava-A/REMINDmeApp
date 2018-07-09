package me.remind;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public abstract class RemindableNote extends Note implements Remindable, Serializable
{
    private static final long serialVersionUID = 1L;
    
    private DateTime deadline;
    
    public RemindableNote(String title, Priority priority)
    {
        super(title, priority);
    }
    
    protected DateTime getDeadline()
    {
        return deadline;
    }
    
    protected void setDeadline(DateTime deadline)
    {
        this.deadline = deadline;
    }
    
    protected long getHoursToDeadline(DateTime deadline)
    {
        return TimeUnit.MILLISECONDS.toHours(
                deadline.minus(System.currentTimeMillis()).toDateTime().getMillis());
    }
    
    @Override
    public void remind()
    {
        System.out.print(" ->[Reminder set in " + getHoursToDeadline(deadline) + " hours]\n");
    }
}
