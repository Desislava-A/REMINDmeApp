package me.remind;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClipBoard
{
    private List<Note> allNotes;
    private List<Remindable> remindableNotes;
    private List<Note> archivedNotes;
    private Set<Note> pinnedNotes;
    
    public ClipBoard()
    {
        setAllNotes(new ArrayList<>());
        setRemindableNotes(new ArrayList<>());
        setArchivedNotes(new ArrayList<>());
        setPinnedNotes(new HashSet<>());
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

    // add 4 methods to construct 4 types of notes
    
    private void setPinnedNotes(Set<Note> pinnedNotes)
    {
        this.pinnedNotes = pinnedNotes;
    }
    
    private void setArchivedNotes(List<Note> archivedNotes)
    {
        this.archivedNotes = archivedNotes;
    }
    
    // add 4 void methods that construct different types of messages (Desi)
    
    
    
    public void search(String title)
    {
        for (Note note : allNotes)
            if (note.getTitle().equals(title))
                note.showNote(note);
    }
    
    public void pinNote(Note note)
    {
        pinnedNotes.add(note);
    }
    
    public void archiveNote(Note note)
    {
        archivedNotes.add(note);
        
        allNotes.remove(note);
    }
}