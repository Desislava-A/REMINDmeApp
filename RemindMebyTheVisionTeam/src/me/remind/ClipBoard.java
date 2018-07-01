package me.remind;

import java.util.*;

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
    
    public List<Note> getArchivedNotes()
    {
        return archivedNotes;
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
        for (Note note: allNotes)
            if (note.getTitle().equals(title))
                note.showNote();
    }
    
    public void showAllNotes()
    {
        System.out.println("\n[Pinned]");
        pinnedNotes.forEach(Note::showNote);
        
        System.out.println("\n[All notes]");
        allNotes.forEach(Note::showNote);
    }
    
    public void showReminders()
    {
        System.out.println("[Reminders]");
        remindableNotes.forEach(Remindable::remind);
    }
    
    public void showTitles()
    {
        allNotes.forEach(Note::printTitle);
    }
    
    public void showPinnedTitles()
    {
        pinnedNotes.forEach(Note::printTitle);
    }
    
    public boolean isNotePinned(String title)
    {
        for (Note note: allNotes)
            if (note.getTitle().equals(title))
                return true;
        
        return false;
    }
    
    public void pinNote(String title)
    {
        for (Note note: allNotes)
            if (note.getTitle().equals(title))
                pinnedNotes.add(note);
    }
    
    public void unpinNote(String title)
    {
        for (Note note: pinnedNotes)
            if (note.getTitle().equals(title))
                pinnedNotes.remove(note);
    }
    
    public void archiveNote(String title)
    {
        for (Note note: allNotes)
        {
            if (note.getTitle().equals(title))
            {
                archivedNotes.add(note);
                allNotes.remove(note);
            }
        }
    }
    
    public boolean isNoteArchived(String title)
    {
        for (Note note: archivedNotes)
            if (note.getTitle().equals(title))
                return true;
    
        return false;
    }
    
    public void deleteNote(String title)
    {
        for (Note note: allNotes)
            if (note.getTitle().equals(title))
                pinnedNotes.remove(note);
    }
    
    public void clearAllNotes()
    {
        allNotes.clear();
        archivedNotes.clear();
    }
    
    public void clearArchive()
    {
        archivedNotes.clear();
    }
}