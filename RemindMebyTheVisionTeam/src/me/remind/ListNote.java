package me.remind;

import java.util.ArrayList;
import java.util.List;

public class ListNote extends Note
{
    private List<String> checkBoxesList;//each index is a new checkbox item

    public List<String> getCheckBoxesList() {
        return new ArrayList<>(checkBoxesList);
    }

    public void setCheckBoxesList(List<String> checkBoxesList) {
        this.checkBoxesList = checkBoxesList;
    }

    ListNote(String title, String deadline, Priority priority, List<String> checkBoxesList) {
        this(title, deadline, priority);
        setCheckBoxesList(new ArrayList<>());

    }

    ListNote(String title, String deadline, Priority priority) {
        super(title, deadline, priority);
    }

    @Override
    public void makeNote() {

    }
    
    @Override
    public void reviewNote()
    {
    
    }
}
