package me.remind;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    
    private void setPinnedNotes(Set<Note> pinnedNotes)
    {
        this.pinnedNotes = pinnedNotes;
    }
    
    private void setArchivedNotes(List<Note> archivedNotes)
    {
        this.archivedNotes = archivedNotes;
    }
    
    public Set<Note> getPinnedNotes()
    {
        return pinnedNotes;
    }
    
    public void addTextNote(String title, Calendar deadline, Priority priority)
    {
        TextNote textNote = new TextNote(title, deadline, priority);
        
        allNotes.add(textNote);
        remindableNotes.add(textNote);
    }
    
    public void addListNote(String title, Calendar deadline, Priority priority)
    {
        ListNote textNote = new ListNote(title, deadline, priority);
        
        allNotes.add(textNote);
        remindableNotes.add(textNote);
    }
    
    public void search(String title)
    {
        for (Note note : allNotes)
            if (note.getTitle().equals(title))
                note.showNote();
    }
    
    public void showAllNotes()
    {
        System.out.println("[Pinned]");
        pinnedNotes.forEach(Note::showNote);
    
        System.out.println("\n[All notes]");
        allNotes.forEach(Note::showNote);
    }
    
    public void showReminders()
    {
        System.out.println("[Reminders]");
        remindableNotes.forEach(Remindable::remind);
    }
    
    public void showTitlesForPin()
    {
        allNotes.forEach(Note::printTitle);
    }
    
    public boolean isNotePinned(String title)
    {
        final Stream<Boolean> booleanStream = pinnedNotes.stream().map(note ->
        {
            if (note.getTitle().equals(title))
                return true;
        
            return false;
        });
    
        return false;
    }
    
    public void pinNote(String title)
    {
        for (Note note : allNotes)
            if (note.getTitle().equals(title))
                pinnedNotes.add(note);
    }
    
    public void archiveNote(Note note)
    {
        archivedNotes.add(note);
        
        allNotes.remove(note);
    }
}