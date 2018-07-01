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
    public void showNote()
    {
        System.out.printf("%s: This is voice note: %s \n", super.toString(),audioFile );
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
