package me.remind;

import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;

public interface Remindable
{
    void remind();
    void setDeadline(DateTime deadline);
    DateTime getDeadline();
    
    default long getHoursToDeadline(DateTime deadline)
    {
        return TimeUnit.MILLISECONDS.toHours(
                deadline.minus(System.currentTimeMillis()).toDateTime().getMillis());
    }
}
