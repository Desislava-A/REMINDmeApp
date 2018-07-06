package me.remind;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public abstract class Note implements Serializable {
    public static final int MIN_TITLE_LENGTH = 3;
    public static final int MAX_TITLE_LENGTH = 60;
    public static final int DEADLINE_LENGTH = 10;

    private String title;
    private DateTime deadline;
    private Priority priority;
    private boolean pinned;

    public Note() {
    }

    public Note(String title, DateTime deadline, Priority priority) {
        setTitle(title);
        setDeadline(deadline);
        setPriority(priority);
        setPinned(false);
    }

    protected String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        if (title == null)
            return;

        this.title = title;
    }

    protected DateTime getDeadline() {
        return deadline;
    }

    private void setDeadline(DateTime deadline) {
        this.deadline = deadline;
    }

    protected Priority getPriority() {
        return priority;
    }

    private void setPriority(Priority priority) {
        this.priority = priority;
    }

    protected void changePriority(Priority priority) {
        if (this.priority != priority)
            this.priority = priority;
    }

    protected boolean isPinned() {
        return pinned;
    }

    protected void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    /**
     * Converts milliseconds to hours -> used as output for reminders
     */
    protected long getHoursToDeadline() {
        return TimeUnit.MILLISECONDS.toHours(
                deadline.minus(System.currentTimeMillis()).toDateTime().getMillis());
    }

    protected void printTitle() {
        System.out.println(getTitle());
    }

    protected abstract void showNote();

    protected abstract String getTitleWithTypeAndPriority();
}
