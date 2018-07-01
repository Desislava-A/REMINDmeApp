package me.remind;

import java.awt.*;
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


    
    private void setPinnedNotes(Set<Note> pinnedNotes)
    {
        this.pinnedNotes = pinnedNotes;
    }
    
    private void setArchivedNotes(List<Note> archivedNotes)
    {
        this.archivedNotes = archivedNotes;
    }
    


    public void makeTextNote (String title, String deadline,String text, Priority priority){
        TextNote textNote = new TextNote(title,deadline,text,priority);
        allNotes.add(textNote);
        remindableNotes.add(textNote);
    }

    public void makePhotoNoteNoText (String title, String deadline, Image photo, Priority priority){
        PhotoNote photoNote = new PhotoNote(title,deadline,photo,priority);
        allNotes.add(photoNote);
    }

    public void makePhotoNoteWithText (String title, String deadline, Image photo, String shortText, Priority priority){
        PhotoNote photoNote = new PhotoNote(title,deadline,photo,shortText,priority);
        allNotes.add(photoNote);
    }

    public void makeVoiceNote (String title, String deadline, String audioFile, Priority priority){
        VoiceNote voiceNote = new VoiceNote(title,deadline,audioFile,priority);
        allNotes.add(voiceNote);
        remindableNotes.add(voiceNote);
    }

    public void makeListNote (String title, String deadline, Priority priority, List<String> checkBoxesList){
        ListNote listNote = new ListNote(title,deadline,priority,checkBoxesList);
        allNotes.add(listNote);
        remindableNotes.add(listNote);
    }
    

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