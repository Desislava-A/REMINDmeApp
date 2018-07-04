package me.remind;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClipBoard implements Enumeration<Note>
{
    private List<Note> allNotes;
    private List<Remindable> remindableNotes;
    private List<ListNote> listNotes;
    private List<Note> archivedNotes;
    private Set<Note> pinnedNotes;
    private static int iteratorIndex;
    private static int index;
    
    public ClipBoard()
    {
        setAllNotes(new ArrayList<>());
        setRemindableNotes(new ArrayList<>());
        setArchivedNotes(new ArrayList<>());
        setListNotes(new ArrayList<>());
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
    }
    
    /**
     * Method that constructs a voiceNote object
     */
    protected void addVoiceNote(String title, Date deadline, Priority priority, String audioFile)
    {
        VoiceNote voiceNote = new VoiceNote(title, deadline, priority, audioFile);
        
        allNotes.add(voiceNote);
        remindableNotes.add(voiceNote);
    }
    
    /**
     * Method that prints the contents of a note
     *
     * @param title - the title to search
     */
    protected void search(String title)
    {
        allNotes.stream()
                .filter(note -> note.getTitle().equals(title))
                .findAny().ifPresentOrElse(Note::showNote, null);
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
        
        index = 1;
        System.out.println("\n[All notes]");
        allNotes.forEach(note -> System.out.println(index++ + "." +
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
        System.out.println("\n[Titles]");
        allNotes.forEach(note -> System.out.println("\t" + note.getTitle()));
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
        return pinnedNotes.stream()
                .anyMatch(note -> note.getTitle().equals(title));
    }
    
    /**
     * Method that pins a note
     *
     * @param title - the title of the note to be pinned
     */
    protected void pinNote(String title)
    {
        pinnedNotes.add(allNotes.stream()
                .filter(note ->
                {
                    if (note.getTitle().equals(title))
                    {
                        note.setPinned(true);
                        return true;
                    }
                    
                    return false;
                })
                .findAny()
                .orElseThrow(NoSuchElementException::new));
    }
    
    /**
     * Method that unpins a note by title
     *
     * @param title - the title of the note to be unpinned
     */
    protected void unpinNote(String title)
    {
        pinnedNotes.remove(pinnedNotes.stream()
                .filter(pinned -> pinned.getTitle().equals(title))
                .findAny()
                .orElse(null));
    }
    
    /**
     * Method that archives a note by title
     *
     * @param title - the title of the note to be archived
     */
    protected void archiveNote(String title)
    {
        allNotes.stream()
                .filter(note -> note.getTitle().equals(title))
                .findAny()
                .ifPresent(note ->
                {
                    allNotes.remove(note);
                    
                    if (note.isPinned())
                        pinnedNotes.remove(note);
                });
    }
    
    /**
     * Method that searches for a note and prompts to change its inner items' status
     *
     * @param title - the tile of the note to be checked
     */
    protected void promptToCheckListItems(String title)
    {
        listNotes.stream()
                .filter(note -> note.getTitle().equals(title))
                .findAny()
                .orElse(null)
                .promptToChangeStatus();
    }
    
    /**
     * Method that checks if a note has been successfully archived
     *
     * @param title - the title of the note to be checked
     * @return - true if a matching title is found
     */
    protected boolean isNoteArchived(String title)
    {
        return archivedNotes.stream()
                .anyMatch(note -> note.getTitle().equals(title));
    }
    
    /**
     * Method that deltetes a note by title
     * If present, deletes the object in the other data structures
     *
     * @param title - the title of the note to be deleted
     */
    protected void deleteNote(String title)
    {
        AtomicBoolean isPinned = new AtomicBoolean(false);
        AtomicBoolean isRemindable = new AtomicBoolean(false);
        AtomicBoolean isList = new AtomicBoolean(false);
        
        allNotes.stream()
                .filter(note -> note.getTitle().equals(title))
                .findAny()
                .ifPresentOrElse(note ->
                {
                    if (note instanceof ListNote)
                        isList.set(true);
                    
                    if (note.isPinned())
                        isPinned.set(true);
                    
                    if (note instanceof Remindable)
                        isRemindable.set(true);
                    
                    allNotes.remove(note);
                }, null);
        
        if (isList.get())
            listNotes.remove(listNotes.stream()
                    .filter(note -> note.getTitle().equals(title))
                    .findAny());
        
        if (isPinned.get())
            pinnedNotes.remove(pinnedNotes.stream()
                    .filter(note -> note.getTitle().equals(title))
                    .findAny());
        
        if (isRemindable.get())
            remindableNotes.remove(remindableNotes.stream()
                    .filter(note -> note.toString().equals(title))
                    .findAny());
    }
    
    /**
     * Method to clear the main data structure
     */
    protected void clearAllNotes()
    {
        if (allNotes.size() > 0)
        {
            allNotes.clear();
            archivedNotes.clear();
            remindableNotes.clear();
        } else
            System.out.println("Clipboard is already empty!");
    }
    
    /**
     * Method to clear the archive data structure
     */
    protected void clearArchive()
    {
        if (archivedNotes.size() > 0)
            archivedNotes.clear();
        else
            System.err.println("Archive is already empty!");
    }
    
    /**
     * Method to clear the remindables data structure
     */
    protected void clearReminders()
    {
        if (remindableNotes.size() > 0)
            remindableNotes.clear();
        else
            System.err.println("\nNo reminders to clear!");
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
