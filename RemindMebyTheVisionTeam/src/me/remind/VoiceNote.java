package me.remind;

import org.joda.time.DateTime;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.FileNotFoundException;

public class VoiceNote extends RemindableNote
{
    private String fileName;
    private DateTime deadline;
    private transient AudioInputStream inputStream;
    private transient Clip audioClip;
    
    public VoiceNote(String title, DateTime deadline, Priority priority, String fileName)
            throws IOException, UnsupportedAudioFileException, LineUnavailableException
    {
        super(title, priority);
        setDeadline(deadline);
        setFileName(fileName);
        initializeVoiceFile(fileName);
    }
    
    protected String getFileName()
    {
        return fileName;
    }
    
    private void setFileName(String audioFilePath)
    {
        this.fileName = audioFilePath;
    }
    
    private void setInputStream(AudioInputStream inputStream)
    {
        this.inputStream = inputStream;
    }
    
    private void setAudioClip(Clip audioClip)
    {
        this.audioClip = audioClip;
    }
    
    /**
     * Audio file initializer
     *
     * @param fileName - files are situated in res/sounds/
     * @throws FileNotFoundException if the audio file doesn't exist
     */
    private void initializeVoiceFile(final String fileName) throws IOException,
            UnsupportedAudioFileException, LineUnavailableException
    {
        setInputStream(AudioSystem.getAudioInputStream(Main.class.getResourceAsStream(
                "/res/sounds/" + fileName)));
        
        setAudioClip(AudioSystem.getClip());
    }
    
    @Override
    protected String getTitleWithTypeAndPriority()
    {
        return "[VoiceNote] {Priority: " + getPriority()
                .toString().toLowerCase() + "}" +
                "\n\t\t" + getTitle();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof VoiceNote &&
                ((VoiceNote) obj).getFileName().equals(getFileName());
    }
    
    @Override
    public int hashCode()
    {
        return getUid().hashCode();
    }
    
    @Override
    public String toString()
    {
        return getTitle();
    }
    
    @Override
    protected void showNote()
    {
        try
        {
            audioClip.open(inputStream);
        } catch (IOException ioex)
        {
            ioex.printStackTrace();
        } catch (LineUnavailableException lineex)
        {
            lineex.printStackTrace();
        }
        
        audioClip.setFramePosition(0);
        audioClip.start();
    }
}
