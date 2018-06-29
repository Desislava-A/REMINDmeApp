package me.remind;

import java.util.ArrayList;
import java.util.List;

public class ListNote extends Note
{
    private List<String> checkBoxesList; //each index is a new checkbox item
    
    public List<String> getCheckBoxesList()
    {
        return new ArrayList<>(checkBoxesList);
    }
    
    public void setCheckBoxesList(List<String> checkBoxesList)
    {
        this.checkBoxesList = checkBoxesList;
    }
    
    public ListNote(String title, String deadline, Priority priority, List<String> checkBoxesList)
    {
        this(title, deadline, priority);
        setCheckBoxesList(new ArrayList<>());
        
    }
    
    public ListNote(String title, String deadline, Priority priority)
    {
        super(title, deadline, priority);
    }
    
    @Override
    public void showNote(Note note)
    {
    
    }
    
    @Override
    public void previewNote()
    {
    
    }
}