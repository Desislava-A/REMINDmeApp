package me.remind;

import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;

/**
 * Created by Teodor on 7/9/2018.
 */
public class Reminder {
    static final int HOURS_TO_REMIND = 24;

    public String remindMessage(Note note) {
        return "Dont forget about this in 24 hours " + note.toString();
    }

    public String nowMessage(Note note) {
        return note.toString() + "is due now!";
    }

    public void remindOneDayBefore(ClipBoard clipboard) {
        for (Note note : clipboard.getAllNotes()) {
            if (note.getHoursToDeadline() == HOURS_TO_REMIND) {
                System.out.println(remindMessage(note));
            }
        }
    }

    public void remindNow(ClipBoard clipboard) {

        for (Note note : clipboard.getAllNotes()) {
            DateTime deadline = note.getDeadline();
            if (TimeUnit.MILLISECONDS.toSeconds(
                    deadline.minus(System.currentTimeMillis()).toDateTime().getMillis()) < 2) {

                System.out.println(nowMessage(note));
            }
        }
    }
}
