package me.remind;

public abstract class Note {

    private String title;
    private String deadline;

    Note(String title, String deadline) {
    this.title=title;
    this.deadline=deadline;
    }

    @Override
    public String toString(){
        return String.format("[ %s ] until: %s",title,deadline);
    }

    public abstract void makeNote();

}
