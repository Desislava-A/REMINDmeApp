package me.remind;

import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;

/**
 * Created by Teodor on 7/9/2018.
 */
public class Reminder
{
    private static final int HOURS_TO_REMIND = 24;
    
    public String remindMessage(RemindableNote note)
    {
        return "Don't forget about this in 24 hours: " + note.toString();
    }
    
    public String nowMessage(RemindableNote note)
    {
        return note.toString() + " is due now!";
    }
    
    public void remindOneDayBefore(ClipBoard clipboard)
    {
        clipboard.getRemindableNotes().stream()
                .filter(note -> note.getHoursToDeadline(
                        note.getDeadline()) == HOURS_TO_REMIND)
                .forEach(this::remindMessage);
    }
    
    public void remindNow(ClipBoard clipboard)
    {
        clipboard.getRemindableNotes().forEach(note ->
        {
            DateTime deadline = note.getDeadline();
            
            if (TimeUnit.MILLISECONDS.toSeconds(
                    deadline.minus(System.currentTimeMillis())
                            .toDateTime().getMillis()) < 2)
                System.out.println(nowMessage(note));
        });
    }
}