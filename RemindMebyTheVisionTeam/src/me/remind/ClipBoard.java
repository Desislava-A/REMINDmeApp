package me.remind;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClipBoard implements Iterable<Note>
{
    private LinkedList<Note> allNotes;
    private Set<Remindable> remindableNotes;
    private Set<ListNote> listNotes;
    private Set<Note> archivedNotes;
    private Set<Note> pinnedNotes;
    private static int iteratorIndex;
    private static int index;
    
    public ClipBoard()
    {
        setAllNotes(new LinkedList<>());
        setRemindableNotes(new LinkedHashSet<>());
        setArchivedNotes(new LinkedHashSet<>());
        setListNotes(new LinkedHashSet<>());
        setPinnedNotes(new LinkedHashSet<>());
    }
    
    public LinkedList<Note> getAllNotes()
    {
        return new LinkedList<>(allNotes);
    }
    
    private void setAllNotes(LinkedList<Note> allNotes)
    {
        this.allNotes = allNotes;
    }
    
    public Set<Remindable> getRemindableNotes()
    {
        return new LinkedHashSet<>(remindableNotes);
    }
    
    private void setRemindableNotes(Set<Remindable> remindableNotes)
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
    
    private void setArchivedNotes(Set<Note> archivedNotes)
    {
        this.archivedNotes = archivedNotes;
    }
    
    public Set<Note> getArchivedNotes()
    {
        return new LinkedHashSet<>(archivedNotes);
    }
    
    public Set<ListNote> getListNotes()
    {
        return new LinkedHashSet<>(listNotes);
    }
    
    private void setListNotes(Set<ListNote> listNotes)
    {
        this.listNotes = listNotes;
    }
    
    /**
     * Method that constructs a textNote object and adds it to the data structures
     */
    protected void addTextNote(String title, Date deadline, Priority priority)
    {
        TextNote textNote = new TextNote(title, deadline, priority);
        
        allNotes.addLast(textNote);
        remindableNotes.add(textNote);
    }
    
    /**
     * Method that constructs a listNote object and adds it to the data structures
     */
    protected void addListNote(String title, Date deadline, Priority priority)
    {
        ListNote listNote = new ListNote(title, deadline, priority);
        
        allNotes.addLast(listNote);
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
        
        allNotes.addLast(photoNote);
    }
    
    /**
     * Method that constructs a voiceNote object
     */
    protected void addVoiceNote(String title, Date deadline, Priority priority, String audioFile)
    {
        VoiceNote voiceNote = new VoiceNote(title, deadline, priority, audioFile);
        
        allNotes.addLast(voiceNote);
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
                .findAny()
                .orElseThrow(NoSuchElementException::new);
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
     * Method that prints all archived notes' titles
     */
    protected void showArchive()
    {
        System.out.println("\n[Archive]");
        archivedNotes.forEach(note -> System.out.println("\t " + note.getTitle()));
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
                .orElseThrow(NoSuchElementException::new));
    }
    
    /**
     * Method that archives a note by title
     *
     * @param title - the title of the note to be archived
     */
    protected void archiveNote(String title)
    {
        allNotes.stream()
                .filter(note ->
                {
                    if (note.getTitle().equals(title))
                    {
                        allNotes.remove(note);
                        
                        if (note.isPinned())
                            pinnedNotes.remove(note);
                        
                        return true;
                    }
                    
                    return false;
                })
                .findAny()
                .orElseThrow(NoSuchElementException::new);
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
                .orElseThrow(NoSuchElementException::new)
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
                .filter(note ->
                {
                    if (note.getTitle().equals(title)) ;
                    {
                        if (note instanceof ListNote)
                            isList.set(true);
                        
                        if (note.isPinned())
                            isPinned.set(true);
                        
                        if (note instanceof Remindable)
                            isRemindable.set(true);
                        
                        allNotes.remove(note);
                        
                        return true;
                    }
                })
                .findAny()
                .orElseThrow(NoSuchElementException::new);
        
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
            throw new IllegalStateException("Clipboard is already empty!");
    }
    
    /**
     * Method to clear the archive data structure
     */
    protected void clearArchive()
    {
        if (archivedNotes.size() > 0)
            archivedNotes.clear();
        else
            throw new IllegalStateException("Archive is already empty!");
    }
    
    /**
     * Method to clear the remindables data structure
     */
    protected void clearReminders()
    {
        if (remindableNotes.size() > 0)
            remindableNotes.clear();
    }
    
    @Override
    public Iterator<Note> iterator()
    {
        return allNotes.iterator();
    }
}
