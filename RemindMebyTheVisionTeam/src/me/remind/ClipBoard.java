package me.remind;

import java.util.ArrayList;
import java.util.List;

public class ClipBoard
{
    private List<Note> allNotes;
    private List<Remindable> remindableNotes;
    
    public ClipBoard()
    {
        setAllNotes(new ArrayList<>());
        setRemindableNotes(new ArrayList<>());
    }
    
    public List<Note> getAllNotes()
    {
        return new ArrayList<>(allNotes);
    }
    
    private void setAllNotes(List<Note> allNotes)
    {
        this.allNotes = allNotes;
    }
    
    public List<Remindable> getRemindableNotes()
    {
        return new ArrayList<>(remindableNotes);
    }
    
    private void setRemindableNotes(List<Remindable> remindableNotes)
    {
        this.remindableNotes = remindableNotes;
    }
    
    public void search(String title)
    {
        for (Note note : allNotes)
            if (note.getTitle().equals(title))
                note.showNote(note);
    }
}
