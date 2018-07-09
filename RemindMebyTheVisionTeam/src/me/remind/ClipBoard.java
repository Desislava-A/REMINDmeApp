package me.remind;

import org.joda.time.DateTime;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.Serializable;
import java.util.*;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;

/**
 * The clipboaard is the main connecting class, a container
 * for the different kinds of notes and their behavior
 */
public class ClipBoard implements Iterable<Note>, Serializable
{
    private static final long serialVersionUID = 1L;
    
    private Map<Note, String> allNotes;
    private Map<Remindable, String> remindableNotes;
    private Map<Note, String> archivedNotes;
    private Map<Note, String> pinnedNotes;
    private static ClipboardFileManager cfm = new ClipboardFileManager();
    
    public ClipBoard()
    {
        setAllNotes(new LinkedHashMap<>());
        setRemindableNotes(new LinkedHashMap<>());
        setArchivedNotes(new LinkedHashMap<>());
        setPinnedNotes(new LinkedHashMap<>());
    }
    
    protected Map<Note, String> getAllNotes()
    {
        return allNotes;
    }
    
    private void setAllNotes(Map<Note, String> allNotes)
    {
        this.allNotes = allNotes;
    }
    
    protected Map<Remindable, String> getRemindableNotes()
    {
        return remindableNotes;
    }
    
    private void setRemindableNotes(Map<Remindable, String> remindableNotes)
    {
        this.remindableNotes = remindableNotes;
    }
    
    protected Map<Note, String> getArchivedNotes()
    {
        return archivedNotes;
    }
    
    private void setArchivedNotes(Map<Note, String> archivedNotes)
    {
        this.archivedNotes = archivedNotes;
    }
    
    protected Map<Note, String> getPinnedNotes()
    {
        return pinnedNotes;
    }
    
    private void setPinnedNotes(Map<Note, String> pinnedNotes)
    {
        this.pinnedNotes = pinnedNotes;
    }
    
    /**
     * Method that constructs a textNote object
     */
    protected void addTextNote(String title, DateTime deadline, Priority priority)
            throws IOException
    {
        TextNote textNote = new TextNote(title, deadline, priority);
        
        allNotes.put(textNote, textNote.getUid());
        
        // notes without deadlines aren't added to the remindables data structure
        if (deadline != null)
            remindableNotes.put(textNote, textNote.getUid());
        
        cfm.serialize(this);
    }
    
    /**
     * Method that constructs a ListNote object
     */
    protected void addListNote(String title, DateTime deadline, Priority priority)
            throws IOException
    {
        ListNote listNote = new ListNote(title, deadline, priority);
        
        allNotes.put(listNote, listNote.getUid());
        
        if (deadline != null)
            remindableNotes.put(listNote, listNote.getUid());
        
        cfm.serialize(this);
    }
    
    /**
     * Method that constructs a PhotoNote object
     *
     * @throws FileNotFoundException if the image file doesn't exist
     */
    protected void addPhotoNote(String title, Priority priority, String filePath,
                                String description) throws IOException
    {
        PhotoNote photoNote = new PhotoNote(title, priority, filePath, description);
        
        allNotes.put(photoNote, photoNote.getUid());
        
        cfm.serialize(this);
    }
    
    /**
     * Method that constructs a voiceNote object
     *
     * @throws FileNotFoundException if the voice file doesn't exist
     */
    protected void addVoiceNote(String title, DateTime deadline, Priority priority, String audioFile)
            throws IOException, LineUnavailableException, UnsupportedAudioFileException
    {
        VoiceNote voiceNote = new VoiceNote(title, deadline, priority, audioFile);
        
        allNotes.put(voiceNote, voiceNote.getUid());
        
        if (deadline != null)
            remindableNotes.put(voiceNote, voiceNote.getUid());
        
        cfm.serialize(this);
    }
    
    /**
     * Method that collects all different notes with the same title
     *
     * @param title - the title that's being searched
     * @return a list containing all note objects that match the title
     */
    protected List<Note> search(String title)
    {
        return allNotes.keySet().stream()
                .filter(note -> note.getTitle().equals(title))
                .collect(Collectors.toList());
    }
    
    /**
     * Method that prints all pinned notes' titles
     */
    protected void showPinnedTitles()
    {
        System.out.println("\n[Pinned]\n");
        pinnedNotes.keySet().forEach(note -> System.out.println("\t" +
                note.getTitleWithTypeAndPriority()));
    }
    
    /**
     * Method that prints the contents of all pinned and unpinned notes
     */
    protected void showAllNotes()
    {
        // if there are no pinned notes, the pinned section isn't shown
        if (pinnedNotes.size() > 0)
            showPinnedTitles();
        
        /* an anonymous object used to bypass the
        inability to increment in a lambda expression) */
        var ref = new Object()
        {
            int index = 1;
        };
        
        System.out.println("\n[All notes]\n");
        allNotes.keySet().forEach(note -> System.out.println(ref.index++ + "." +
                note.getTitleWithTypeAndPriority()));
    }
    
    /**
     * Method that prints all set reminders
     */
    protected void showReminders()
    {
        System.out.println("\n[Reminders]\n");
        remindableNotes.keySet().forEach(note ->
        {
            System.out.print("\t" + note.toString());
            note.remind();
        });
    }
    
    /**
     * Method that prints all archived notes' titles
     */
    protected void showArchive()
    {
        System.out.println("\n[Archive]\n");
        archivedNotes.keySet().forEach(note -> System.out.println("\t" +
                note.getTitleWithTypeAndPriority()));
    }
    
    /**
     * Method that pins a note
     *
     * @param note the Note object to be pinned
     */
    protected void pinNote(Note note) throws IOException
    {
        note.setPinned(true);
        pinnedNotes.put(note, note.getUid());
        
        cfm.serialize(this);
    }
    
    /**
     * Method that unpins a note
     *
     * @param note the note object to be unpinned
     */
    protected void unpinNote(Note note) throws IOException
    {
        if (note.isPinned())
        {
            if (pinnedNotes.containsValue(note.getUid()))
                pinnedNotes.remove(note, note.getUid());
            
            note.setPinned(false);
        }
        
        cfm.serialize(this);
    }
    
    /**
     * Method that archives a note
     *
     * @param note the Note object to be archived
     */
    protected void archiveNote(Note note) throws IOException
    {
        note.setArchived(true);
        
        archivedNotes.put(note, note.getUid());
        allNotes.remove(note, note.getUid());
        
        cfm.serialize(this);
    }
    
    /**
     * Method that restores a note from the archive
     *
     * @param note the Note object to be archived
     */
    protected void restoreNoteFromArchive(Note note) throws IOException
    {
        note.setArchived(false);
        
        archivedNotes.remove(note, note.getUid());
        allNotes.put(note, note.getUid());
        
        cfm.serialize(this);
    }
    
    /**
     * Method to initiate a list's checkbox status change
     *
     * @param note the ListNote object that will undergo changes
     * @throws InvalidObjectException if the parameter is not a ListNote
     */
    protected void promptToCheckListItems(Note note) throws IOException
    {
        if (!(note instanceof ListNote))
            throw new InvalidObjectException("Given note is not of type checkbox list");
        
        ((ListNote) note).promptToChangeStatus();
        
        cfm.serialize(this);
    }
    
    /**
     * Method that deletes a note from all data structures it's present in
     *
     * @param note the Note object to be deleted
     */
    protected void deleteNote(Note note) throws IOException
    {
        allNotes.remove(note, note.getUid());
        
        if (note.isPinned())
            pinnedNotes.remove(note, note.getUid());
        
        if (((Remindable) note).getDeadline() != null)
            remindableNotes.remove(note, note.getUid());
        
        cfm.serialize(this);
    }
    
    /**
     * Method that edits the title of any note
     *
     * @param note     the Note object to be deleted
     * @param newTitle the new title to be set
     */
    protected void editTitle(Note note, String newTitle) throws IOException
    {
        note.setTitle(newTitle);
        
        cfm.serialize(this);
    }
    
    /**
     * Method that edits the text of a text note
     *
     * @param textNote the Note object to be deleted
     * @param text     the new text to be set
     * @throws InvalidObjectException if the given parameter is not the correct type of object
     */
    protected void editText(Note textNote, String text) throws IOException
    {
        if (textNote instanceof TextNote)
            ((TextNote) textNote).setText(text);
        else
            throw new InvalidObjectException("Only text notes have text!");
        
        cfm.serialize(this);
    }
    
    /**
     * Method that edits the description of a photo note
     *
     * @param photoNote   the Note object to be deleted
     * @param description the new description
     * @throws InvalidObjectException if the given parameter is not the correct type of object
     */
    protected void editDescription(Note photoNote, String description)
            throws IOException, InvalidObjectException
    {
        if (photoNote instanceof PhotoNote)
            ((PhotoNote) photoNote).setDescription(description);
        else
            throw new InvalidObjectException("Only photo notes have descriptions!");
        
        cfm.serialize(this);
    }
    
    /**
     * Method that edits a note's priority
     *
     * @param note     the targeted note object
     * @param priority the new priority to be set
     */
    protected void editPriority(Note note, Priority priority) throws IOException
    {
        if (note.getPriority() != priority)
            note.setPriority(priority);
        
        cfm.serialize(this);
    }
    
    /**
     * Method that edits the deadline of a reminder
     *
     * @param note     the targeted Remindable type object
     * @param deadline the new deadline to be set
     */
    protected void editDeadline(Remindable note, DateTime deadline) throws IOException
    {
        note.setDeadline(deadline);
        cfm.serialize(this);
    }
    
    /**
     * Method that clears the main data structure
     */
    protected void clearAllNotes() throws IOException
    {
        if (allNotes.size() > 0)
        {
            allNotes.clear();
            archivedNotes.clear();
            remindableNotes.clear();
        } else
            throw new IllegalStateException("Clipboard is already empty!");
        
        cfm.serialize(this);
    }
    
    /**
     * Method that clears the archive data structure
     */
    protected void clearArchive() throws IOException
    {
        if (archivedNotes.size() > 0)
            archivedNotes.clear();
        else
            throw new IllegalStateException("Archive is already empty!");
        
        cfm.serialize(this);
    }
    
    /**
     * Method that clears the remindables data structure
     */
    protected void clearReminders() throws IOException
    {
        if (remindableNotes.size() > 0)
            remindableNotes.clear();
        else
            throw new IllegalStateException("Reminders is already empty!");
        
        cfm.serialize(this);
    }
    
    /**
     * The main clipboard iterator
     *
     * @return - the iterator object of all the keys in the biggest data structure allNotes
     */
    @Override
    public Iterator<Note> iterator()
    {
        return allNotes.keySet().iterator();
    }
}
