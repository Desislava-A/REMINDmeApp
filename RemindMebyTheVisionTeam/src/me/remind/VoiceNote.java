package me.remind;

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
    
    public VoiceNote(String title, String deadline, String audioFile,Priority priority)
    {
        this(title, deadline, priority);
        setAudioFile(audioFile);
    }
    
    public VoiceNote(String title, String deadline, Priority priority)
    {
        super(title, deadline, priority);
    }
    
    @Override
    public void showNote(Note note)
    {
    
    }
    
    @Override
    public void previewNote()
    {
    
    }
    
    @Override
    public void remind()
    {
    
    }
}
