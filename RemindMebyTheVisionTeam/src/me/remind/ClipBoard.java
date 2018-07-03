package me.remind;

import java.util.*;

public class ClipBoard implements Enumeration<Note>
{
    private List<Note> allNotes;
    private List<Remindable> remindableNotes;
    private List<ListNote> listNotes;
    private List<PhotoNote> images;
    private List<VoiceNote> voiceRecords;
    private List<Note> archivedNotes;
    private Set<Note> pinnedNotes;
    private static int iteratorIndex = 0;
    private static int indexGetter = 0;
    
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
    
    private void setImages(List<PhotoNote> images)
    {
        this.images = images;
    }
    
    public List<PhotoNote> getImages()
    {
        return new ArrayList<>(images);
    }
    
    private void setVoiceRecords(List<VoiceNote> voiceRecords)
    {
        this.voiceRecords = voiceRecords;
    }
    
    public List<VoiceNote> getVoiceRecords()
    {
        return new ArrayList<>(voiceRecords);
    }
    
    public Set<Note> getPinnedNotes()
    {
        return new LinkedHashSet<>(pinnedNotes);
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
        return new ArrayList<>(archivedNotes);
    }
    
    public List<ListNote> getListNotes()
    {
        return new ArrayList<>(listNotes);
    }
    
    private void setListNotes(List<ListNote> listNotes)
    {
        this.listNotes = listNotes;
    }
    
    /**
     * Method that constructs a textNote object and adds it to the data structures
     */
    protected void addTextNote(String title, Date deadline, Priority priority)
    {
        TextNote textNote = new TextNote(title, deadline, priority);
        
        allNotes.add(textNote);
        remindableNotes.add(textNote);
    }
    
    /**
     * Method that constructs a listNote object and adds it to the data structures
     */
    protected void addListNote(String title, Date deadline, Priority priority)
    {
        ListNote listNote = new ListNote(title, deadline, priority);
        
        allNotes.add(listNote);
        remindableNotes.add(listNote);
        listNotes.add(listNote);
    }
    
    /**
     * Method that constructs a photoNote object
     */
    protected void addPhotoNote(String title, Date deadline, Priority priority,
                                String filePath, String description)
    {
        PhotoNote photoNote = new PhotoNote(title, deadline, priority,
                filePath, description);
        
        allNotes.add(photoNote);
        images.add(photoNote);
    }
    
    /**
     * Method that constructs a voiceNote object
     */
    protected void addVoiceNote(String title, Date deadline, Priority priority, String audioFile)
    {
        VoiceNote voiceNote = new VoiceNote(title, deadline, priority, audioFile);
        
        allNotes.add(voiceNote);
        remindableNotes.add(voiceNote);
        voiceRecords.add(voiceNote);
    }
    
    /**
     * Method that prints the contents of a note
     * @param title - the title to search
     */
    protected void search(String title)
    {
        for (Note note: allNotes)
            if (note.getTitle().equals(title))
                note.showNote();
    }
    
    /**
     * Method that prints the contents of all pinned and unpinned notes
     */
    protected void showAllNotes()
    {
        if (pinnedNotes.size() > 0)
        {
            System.out.println("\n[Pinned]");
            pinnedNotes.forEach(Note::getTitleWithType);
        }
        
        indexGetter = 1;
        System.out.println("\n[All notes]");
        allNotes.forEach(note -> System.out.println(indexGetter++ + "." +
                note.getTitleWithType()));
    }
    
    /**
     * Method that prints all set reminders
     */
    protected void showReminders()
    {
        System.out.println("\n[Reminders]");
        remindableNotes.forEach(reminder -> System.out.println("\t" + reminder.toString()));
    }
    
    /**
     * Method that prints all notes' titles
     * Invoked prior to various different structural changes within the app
     */
    protected void showTitles()
    {
        allNotes.forEach(Note::printTitle);
    }
    
    /**
     * Method that prints all titles of ListNote objects
     */
    protected void showListTitles()
    {
        System.out.println("\n[Titles]");
        listNotes.forEach(note -> System.out.println("\t" + note.getTitle()));
    }
    
    /**
     * Method that prints all pinned notes' titles
     * Invoked prior to pinning a certain note
     */
    protected void showPinned()
    {
        System.out.println("\n[Pinned]");
        pinnedNotes.forEach(note -> System.out.println("\t" + note.getTitle()));
    }
    
    /**
     * Method that prints all archived notes' titles
     */
    protected void showArchive()
    {
        System.out.println("\n[Archive]");
        archivedNotes.forEach(note -> System.out.println("\t " + note.getTitle()));
    }
    
    /**
     * Method that checks if a note has been successfully pinned
     *
     * @param title - the title of the note to be checked
     * @return - true if a matching title is found
     */
    protected boolean isNotePinned(String title)
    {
        for (Note note: pinnedNotes)
            if (note.getTitle().equals(title))
                return true;
        
        return false;
    }
    
    /**
     * Method that pins a note
     *
     * @param title - the title of the note to be pinned
     */
    protected void pinNote(String title)
    {
        for (Note note: allNotes)
            if (note.getTitle().equals(title))
            {
                note.setPinned(true);
                pinnedNotes.add(note);
            }
    }
    
    /**
     * Method that unpins a note by title
     *
     * @param title - the title of the note to be unpinned
     */
    protected void unpinNote(String title)
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
    protected void archiveNote(String title)
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
    
    /**
     * Method that searches for a note and prompts to change its inner items' status
     *
     * @param title - the tile of the note to be checked
     */
    protected void promptToCheckListItems(String title)
    {
        for (ListNote list: listNotes)
            if (list.getTitle().equals(title))
                list.promptToChangeStatus();
    }
    
    /**
     * Method that checks if a note has been successfully archived
     *
     * @param title - the title of the note to be checked
     * @return - true if a matching title is found
     */
    protected boolean isNoteArchived(String title)
    {
        for (Note note: archivedNotes)
            if (note.getTitle().equals(title))
                return true;
        
        return false;
    }
    
    /**
     * Method that deltetes a note by title
     * If present, deletes the object in the other data structures
     *
     * @param title - the title of the note to be deleted
     */
    protected void deleteNote(String title)
    {
        boolean isRecord = false;
        boolean isImage = false;
        boolean isPinned = false;
        boolean isRemindable = false;
        boolean isList = false;
        
        for (Note note: allNotes)
            if (note.getTitle().equals(title))
            {
                if (note instanceof VoiceNote)
                    isRecord = true;
                else if (note instanceof PhotoNote)
                    isImage = true;
                else if (note instanceof ListNote)
                    isList = true;
                
                if (note.isPinned())
                    isPinned = true;
                
                if (note instanceof Remindable)
                    isRemindable = true;
                
                allNotes.remove(note);
            }
        
        if (isList)
            for (ListNote note: listNotes)
                if (note.getTitle().equals(title))
                    pinnedNotes.remove(note);
        
        if (isPinned)
            for (Note note: pinnedNotes)
                if (note.getTitle().equals(title))
                    pinnedNotes.remove(note);
        
        if (isRecord)
            for (VoiceNote record: voiceRecords)
                if (record.getTitle().equals(title))
                    voiceRecords.remove(record);
        
        if (isImage)
            for (PhotoNote img: images)
                if (img.getTitle().equals(title))
                    images.remove(img);
        
        if (isRemindable)
            for (Remindable note: remindableNotes)
                if (note.toString().equals(title))
                    remindableNotes.remove(note);
    }
    
    /**
     * Method to clear the main data structure
     */
    protected void clearAllNotes()
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
    protected void clearArchive()
    {
        archivedNotes.clear();
    }
    
    /**
     * Method to clear the remindables data structure
     */
    protected void clearReminders()
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
