package me.remind;

import java.util.Date;

public class VoiceNote extends Note implements Remindable
{
    private String audioFile;
    
    private String getAudioFile()
    {
        return audioFile;
    }
    
    private void setAudioFile(String audioFile)
    {
        this.audioFile = audioFile;
    }
    
    public VoiceNote(String title, Date deadline, Priority priority, String audioFile)
    {
        this(title, deadline, priority);
        setAudioFile(audioFile);
    }
    
    public VoiceNote(String title, Date deadline, Priority priority)
    {
        super(title, deadline, priority);
    }
    
    @Override
    public void showNote()
    {
    
    }
    
    @Override
    public void remind()
    {
    
    }
}
