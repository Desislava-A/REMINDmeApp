package me.remind;

import java.util.*;

public class ClipBoard implements Enumeration<Note>
{
    private List<Note> allNotes;
    private List<Remindable> remindableNotes;
    private List<ListNote> listNotes;
    private List<Viewable> images;
    private List<Playable> voiceRecords;
    private List<Note> archivedNotes;
    private Set<Note> pinnedNotes;
    private static int iteratorIndex = 0;
    
    public ClipBoard()
    {
        setAllNotes(new ArrayList<>());
        setRemindableNotes(new ArrayList<>());
        setArchivedNotes(new ArrayList<>());
        setListNotes(new ArrayList<>());
        setImages(new ArrayList<>());
        setVoiceRecords(new ArrayList<>());
        setPinnedNotes(new LinkedHashSet<>());
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
    
    private void setImages(List<Viewable> images)
    {
        this.images = images;
    }
    
    public List<Viewable> getImages()
    {
        return images;
    }
    
    private void setVoiceRecords(List<Playable> voiceRecords)
    {
        this.voiceRecords = voiceRecords;
    }
    
    public List<Playable> getVoiceRecords()
    {
        return voiceRecords;
    }
    
    public Set<Note> getPinnedNotes()
    {
        return pinnedNotes;
    }
    
    private void setPinnedNotes(Set<Note> pinnedNotes)
    {
        this.pinnedNotes = pinnedNotes;
    }
    
    private void setArchivedNotes(List<Note> archivedNotes)
    {
        this.archivedNotes = archivedNotes;
    }
    
    public List<Note> getArchivedNotes()
    {
        return archivedNotes;
    }
    
    public List<ListNote> getListNotes()
    {
        return listNotes;
    }
    
    private void setListNotes(List<ListNote> listNotes)
    {
        this.listNotes = listNotes;
    }
    
    /**
     * Method that constructs a textNote object and adds it to the data structures
     */
    public void addTextNote(String title, Date deadline, Priority priority)
    {
        TextNote textNote = new TextNote(title, deadline, priority);
        
        allNotes.add(textNote);
        remindableNotes.add(textNote);
    }
    
    /**
     * Method that constructs a listNote object and adds it to the data structures
     */
    public void addListNote(String title, Date deadline, Priority priority)
    {
        ListNote listNote = new ListNote(title, deadline, priority);
        
        allNotes.add(listNote);
        remindableNotes.add(listNote);
        listNotes.add(listNote);
    }
    
    /**
     * Method that constructs a photoNote object and adds it to the allNotes list
     */
    public void addPhotoNote(String title, Date deadline, Priority priority,
                             String filePath, String description)
    {
        PhotoNote photoNote = new PhotoNote(title, deadline, priority,
                filePath, description);
        
        allNotes.add(photoNote);
        images.add(photoNote);
    }
    
    /**
     * Method that constructs a voiceNote object and adds it to to the allNotes list
     * and remindableNotes list
     */
    public void addVoiceNote(String title, Date deadline, Priority priority, String audioFile)
    {
        VoiceNote voiceNote = new VoiceNote(title, deadline, priority, audioFile);
        
        allNotes.add(voiceNote);
        remindableNotes.add(voiceNote);
        voiceRecords.add(voiceNote);
    }
    
    
    /**
     * Method that prints the contents of a note searched by title
     */
    public void search(String title)
    {
        for (Note note: allNotes)
            if (note.getTitle().equals(title))
                note.showNote();
    }
    
    /**
     * Method that prints the contents of all pinned and unpinned notes
     */
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
    
    /**
     * Method that prints all notes' titles
     * Invoked prior to various different structural changes within the app
     */
    public void showTitles()
    {
        allNotes.forEach(Note::printTitle);
    }
    
    public void showListTitles()
    {
        System.out.println("\n[Titles]");
        listNotes.forEach(Note::printTitle);
    }
    
    /**
     * Method that prints all pinned notes' titles
     * Invoked prior to pinning a certain note
     */
    public void showPinnedTitles()
    {
        pinnedNotes.forEach(Note::printTitle);
    }
    
    /**
     * Method that prints all archived notes' titles
     *
     */
    public void showArchive()
    {
        archivedNotes.forEach(Note::printTitle);
    }
    
    /**
     * Method that checks if a note has been successfully pinned
     *
     * @param title - the title of the note to be checked
     * @return - true if a positive match for title is found
     */
    public boolean isNotePinned(String title)
    {
        for (Note note: pinnedNotes)
            if (note.getTitle().equals(title))
                return true;
        
        return false;
    }
    
    /**
     * Method that pins a note by title
     *
     * @param title - the title of the note to be pinned
     */
    public void pinNote(String title)
    {
        for (Note note: allNotes)
            if (note.getTitle().equals(title))
                pinnedNotes.add(note);
    }
    
    /**
     * Method that unpins a note by title
     *
     * @param title - the title of the note to be unpinned
     */
    public void unpinNote(String title)
    {
        for (Note note: pinnedNotes)
            if (note.getTitle().equals(title))
                pinnedNotes.remove(note);
    }
    
    /**
     * Method that archives a note by title
     *
     * @param title - the title of the note to be archived
     */
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
    
    public void promptToCheckListItems(String title)
    {
        for (ListNote list: listNotes)
            if (list.getTitle().equals(title))
                list.promptToChangeStatus();
    }
    
    /**
     * Method that checks if a note has been successfully archived
     *
     * @param title - the title of the note to be checked
     * @return - true or false
     */
    public boolean isNoteArchived(String title)
    {
        for (Note note: archivedNotes)
            if (note.getTitle().equals(title))
                return true;
        
        return false;
    }
    
    /**
     * Method that deltetes a note by title
     * Also deletes the note if it's also in the pinned data structure
     *
     * @param title - the title of the note to be deleted
     */
    public void deleteNote(String title)
    {
        for (Note note: allNotes)
            if (note.getTitle().equals(title))
                allNotes.remove(note);
        
        if (isNotePinned(title))
            for (Note note: pinnedNotes)
                if (note.getTitle().equals(title))
                    pinnedNotes.remove(note);
    }
    
    /**
     * Method to clear the main data structure
     */
    public void clearAllNotes()
    {
        allNotes.clear();
        archivedNotes.clear();
        remindableNotes.clear();
        images.clear();
        voiceRecords.clear();
    }
    
    /**
     * Method to clear the archive data structure
     */
    public void clearArchive()
    {
        archivedNotes.clear();
    }
    
    /**
     * Method to clear the remindables data structure
     */
    public void clearReminders()
    {
        remindableNotes.clear();
    }
    
    @Override
    public boolean hasMoreElements()
    {
        return allNotes.get(iteratorIndex) != null;
    }
    
    @Override
    public Note nextElement()
    {
        return allNotes.get(iteratorIndex++);
    }
    
    public Iterator<Note> asIterator()
    {
        iteratorIndex = 0;
        
        Iterator<Note> it = new Iterator<Note>()
        {
            @Override
            public boolean hasNext()
            {
                return hasMoreElements();
            }
            
            @Override
            public Note next()
            {
                return nextElement();
            }
        };
        
        return it;
    }
}
