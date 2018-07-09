package me.remind;

import org.apache.commons.collections4.list.SetUniqueList;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class ClipBoard implements Iterable<Note>, Serializable {
    private SetUniqueList<Note> allNotes;
    private SetUniqueList<Remindable> remindableNotes;
    private SetUniqueList<ListNote> listNotes;
    private SetUniqueList<Note> archivedNotes;
    private SetUniqueList<Note> pinnedNotes;
    private static int index;
    private static ClipboardFileManager cfm = new ClipboardFileManager();

    public ClipBoard() {
        setAllNotes(SetUniqueList.setUniqueList(new ArrayList<>()));
        setRemindableNotes(SetUniqueList.setUniqueList(new ArrayList<>()));
        setArchivedNotes(SetUniqueList.setUniqueList(new ArrayList<>()));
        setListNotes(SetUniqueList.setUniqueList(new ArrayList<>()));
        setPinnedNotes(SetUniqueList.setUniqueList(new ArrayList<>()));
    }

    public SetUniqueList<Note> getAllNotes() {
        return SetUniqueList.setUniqueList(new ArrayList<>(allNotes));
    }

    private void setAllNotes(SetUniqueList<Note> allNotes) {
        this.allNotes = allNotes;
    }

    protected SetUniqueList<Remindable> getRemindableNotes() {
        return SetUniqueList.setUniqueList(new ArrayList<>(remindableNotes));
    }

    private void setRemindableNotes(SetUniqueList<Remindable> remindableNotes) {
        this.remindableNotes = remindableNotes;
    }

    protected SetUniqueList<Note> getPinnedNotes() {
        return SetUniqueList.setUniqueList(new ArrayList<>(pinnedNotes));
    }

    private void setPinnedNotes(SetUniqueList<Note> pinnedNotes) {
        this.pinnedNotes = pinnedNotes;
    }

    private void setArchivedNotes(SetUniqueList<Note> archivedNotes) {
        this.archivedNotes = archivedNotes;
    }

    protected SetUniqueList<Note> getArchivedNotes() {
        return SetUniqueList.setUniqueList(new ArrayList<>(archivedNotes));
    }

    protected SetUniqueList<ListNote> getListNotes() {
        return SetUniqueList.setUniqueList(new ArrayList<>(listNotes));
    }

    private void setListNotes(SetUniqueList<ListNote> listNotes) {
        this.listNotes = listNotes;
    }

    /**
     * Method that constructs a textNote object and adds it to the data structures
     *
     * @throws IOException - regarding the buffered reader
     */
    protected void addTextNote(String title, DateTime deadline, Priority priority)
            throws IOException {
        TextNote textNote = new TextNote(title, deadline, priority);

        allNotes.add(textNote);

        // notes without deadlines aren't added to the remindables data structure
        if (deadline != null)
            remindableNotes.add(textNote);
        cfm.serialize(this);
    }

    /**
     * Method that constructs a listNote object and adds it to the data structures
     */
    protected void addListNote(String title, DateTime deadline, Priority priority) throws IOException {
        ListNote listNote = new ListNote(title, deadline, priority);

        allNotes.add(listNote);
        listNotes.add(listNote);

        if (deadline != null)
            remindableNotes.add(listNote);
        cfm.serialize(this);
    }

    /**
     * Method that constructs a photoNote object
     */
    protected void addPhotoNote(String title, DateTime deadline, Priority priority,
                                String filePath, String description) throws IOException {
        PhotoNote photoNote = new PhotoNote(title, deadline, priority,
                filePath, description);

        allNotes.add(photoNote);
        cfm.serialize(this);
    }

    /**
     * Method that constructs a voiceNote object
     */
    protected void addVoiceNote(String title, DateTime deadline, Priority priority, String audioFile) throws IOException {
        VoiceNote voiceNote = new VoiceNote(title, deadline, priority, audioFile);

        allNotes.add(voiceNote);

        if (deadline != null)
            remindableNotes.add(voiceNote);
        cfm.serialize(this);
    }

    /**
     * Method that prints the contents of a note
     *
     * @param title - the title to search
     */
    protected void search(String title) {
        allNotes.stream()
                .filter(note -> note.getTitle().equals(title))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    /**
     * Method that prints the contents of all pinned and unpinned notes
     */
    protected void showAllNotes() {
        // if there are no pinned notes, the pinned section isn't shown
        if (!pinnedNotes.isEmpty()) {
            System.out.println("\n[Pinned]\n");
            pinnedNotes.forEach(Note::getTitleWithTypeAndPriority);
        }

        // before initiating the printing of all notes, always reset the static index field
        index = 1;
        System.out.println("\n[All notes]\n");
        allNotes.forEach(note -> System.out.println(index++ + "." +
                note.getTitleWithTypeAndPriority()));
    }

    /**
     * Method that prints all set reminders
     */
    protected void showReminders() {
        System.out.println("\n[Reminders]\n");
        remindableNotes.forEach(reminder ->
        {
            System.out.print("\t" + reminder.toString());
            reminder.remind();
        });
    }

    /**
     * Method that prints all notes' titles
     */
    protected void showTitles() {
        System.out.println("\n[Titles]\n");
        allNotes.forEach(note -> System.out.println("\t" + note.getTitle()));
    }

    /**
     * Method that prints all titles of ListNote objects
     */
    protected void showListTitles() {
        System.out.println("\n[Titles]\n");
        listNotes.forEach(note -> System.out.println("\t" + note.getTitle()));
    }

    /**
     * Method that prints all archived notes' titles
     */
    protected void showArchive() {
        System.out.println("\n[Archive]\n");
        archivedNotes.forEach(note -> System.out.println("\t " + note.getTitle()));
    }

    /**
     * Method that prints all pinned notes' titles
     * Invoked prior to pinning a certain note
     */
    protected void showPinned() {
        System.out.println("\n[Pinned]\n");
        pinnedNotes.forEach(note -> System.out.println("\t" + note.getTitle()));
    }

    /**
     * Method that checks if a note has been successfully pinned
     *
     * @param title - the title of the note to be checked
     * @return - true if a matching title is found
     */
    protected boolean isNotePinned(String title) {
        return pinnedNotes.stream()
                .anyMatch(note -> note.getTitle().equals(title));
    }

    /**
     * Method that pins a note
     *
     * @param title - the title of the note to be pinned
     */
    protected void pinNote(String title) throws IOException {
        pinnedNotes.add(allNotes.stream()
                .filter(note ->
                {
                    if (note.getTitle().equals(title)) {
                        note.setPinned(true);
                        return true;
                    }

                    return false;
                })
                .findAny()
                .orElseThrow(NoSuchElementException::new));
        cfm.serialize(this);
    }

    /**
     * Method that unpins a note if an object with a matching title is found
     *
     * @param title - the title of the note to be unpinned
     */
    protected void unpinNote(String title) throws IOException {
        pinnedNotes.remove(pinnedNotes.stream()
                .filter(pinned -> pinned.getTitle().equals(title))
                .findAny()
                .orElseThrow(NoSuchElementException::new));
        cfm.serialize(this);
    }

    /**
     * Method that archives a note if an object with a matching title is found
     *
     * @param title - the title of the note to be archived
     */
    protected void archiveNote(String title) throws IOException {
        allNotes.stream()
                .filter(note ->
                {
                    if (note.getTitle().equals(title)) {
                        allNotes.remove(note);

                        if (note.isPinned())
                            pinnedNotes.remove(note);

                        return true;
                    }

                    return false;
                })
                .findAny()
                .orElseThrow(NoSuchElementException::new);
        cfm.serialize(this);
    }

    /**
     * Searching for a ListNote object by given title to invoke the prompt method
     *
     * @param title - the tile of the note to be checked
     */
    protected void promptToCheckListItems(String title) throws IOException {
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
    protected boolean isNoteArchived(String title) {
        return archivedNotes.stream()
                .anyMatch(note -> note.getTitle().equals(title));
    }

    /**
     * Method that deltetes a note if an object with a matching title is found
     * If present in any other data structure, also deletes it from there
     *
     * @param title - the title of the note to be deleted
     */
    protected void deleteNote(String title) throws IOException {
        /* the following one element arrays are created because of the
           inability to use non final variables in a lambda expression */
        final boolean[] isPinned = {false};
        final boolean[] isRemindable = {false};
        final boolean[] isList = {false};

        allNotes.stream()
                .filter(note ->
                {
                    if (note.getTitle().equals(title)) {
                        if (note instanceof ListNote)
                            isList[0] = true;

                        if (note.isPinned())
                            isPinned[0] = true;

                        if (note instanceof Remindable)
                            isRemindable[0] = true;

                        allNotes.remove(note);

                        return true;
                    }

                    return false;
                })
                .findAny()
                .orElseThrow(NoSuchElementException::new);

        if (isList[0])
            listNotes.remove(listNotes.stream()
                    .filter(note -> note.getTitle().equals(title))
                    .findAny());

        if (isPinned[0])
            pinnedNotes.remove(pinnedNotes.stream()
                    .filter(note -> note.getTitle().equals(title))
                    .findAny());

        if (isRemindable[0])
            remindableNotes.remove(remindableNotes.stream()
                    .filter(note -> note.toString().equals(title))
                    .findAny());
        cfm.serialize(this);
    }

    /**
     * Method that clears the main data structure
     */
    protected void clearAllNotes() throws IOException {
        if (!allNotes.isEmpty()) {
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
    protected void clearArchive() throws IOException {
        if (!archivedNotes.isEmpty())
            archivedNotes.clear();
        else
            throw new IllegalStateException("Archive is already empty!");
        cfm.serialize(this);
    }

    /**
     * Method that clears the remindables data structure
     */
    protected void clearReminders() throws IOException {
        if (!remindableNotes.isEmpty())
            remindableNotes.clear();
        else
            throw new IllegalStateException("Reminders is already empty!");
        cfm.serialize(this);
    }

    /**
     * The main clipboard iterator
     *
     * @return - the iterator object of the main data structure allNotes
     */
    @Override
    public Iterator<Note> iterator() {
        return allNotes.iterator();
    }
}
