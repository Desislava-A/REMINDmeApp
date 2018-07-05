package me.remind;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class Note implements Serializable {
    public static final int MIN_TITLE_LENGTH = 3;
    public static final int MAX_TITLE_LENGTH = 60;
    public static final int DEADLINE_LENGTH = 10;

    private String title;
    private Date deadline;
    private Priority priority;

    public Note() {
    }

    public Note(String title) {
        this(title, null);
    }

    public Note(String title, Date deadline) {
        this(title, deadline, Priority.NONE);
    }

    public Note(String title, Date deadline, Priority priority) {
        setTitle(title);
        setDeadline(deadline);
        setPriority(priority);
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        if (title == null)
            return;

        this.title = title;
    }

    public Date getDeadline() {
        return deadline;
    }

    private void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    private void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void changePriority(Priority priority) {
        if (this.priority != priority)
            this.priority = priority;
    }

    protected long getDaysToDeadline() {
        return TimeUnit.MILLISECONDS.toDays(deadline.getTime() -
                System.currentTimeMillis()) + 1;
    }

    protected void printTitle() {
        System.out.println(getTitle());
    }

    public abstract void showNote();
}
