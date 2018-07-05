package me.remind;

import org.joda.time.DateTime;
import javax.sound.sampled.*;
import java.io.IOException;

public class VoiceNote extends Note implements Remindable
{
    private String fileName;
    private AudioInputStream inputStream;
    private Clip audioClip;
    
    public VoiceNote(String title, DateTime deadline, Priority priority, String fileName)
    {
        super(title, deadline, priority);
        setFileName(fileName);
        initializeVoiceFile(fileName);
    }
    
    public String getFileName()
    {
        return fileName;
    }
    
    private void setFileName(String audioFilePath)
    {
        this.fileName = audioFilePath;
    }
    
    private  void setInputStream(AudioInputStream inputStream)
    {
        this.inputStream = inputStream;
    }
    
    private void setAudioClip(Clip audioClip)
    {
        this.audioClip = audioClip;
    }
    
    /**
     * Audio file initializer
     * @param fileName - files are situated in res/sounds/
     */
    private void initializeVoiceFile(final String fileName)
    {
        try
        {
            setInputStream(AudioSystem.getAudioInputStream(Main.class.getResourceAsStream(
                            "/res/sounds/" + fileName)));
            setAudioClip(AudioSystem.getClip());
        } catch (LineUnavailableException lineex)
        {
            System.out.println(lineex.getMessage());
            lineex.printStackTrace();
        } catch (UnsupportedAudioFileException audioex)
        {
            System.out.println(audioex.getMessage());
            audioex.printStackTrace();
        } catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
    }
    
    @Override
    public String getTitleWithTypeAndPriority()
    {
        return "[VoiceNote] {Priority: " + getPriority().toString().toLowerCase() + "}" +
                "\n\t\t" + getTitle();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof VoiceNote &&
                ((VoiceNote) obj).getTitle().equals(getTitle()) &&
                ((VoiceNote) obj).getPriority().equals(getPriority()) &&
                ((VoiceNote) obj).fileName.equals(fileName);
    }
    
    @Override
    public int hashCode()
    {
        return fileName.hashCode();
    }
    
    @Override
    public String toString()
    {
        return getTitle();
    }
    
    @Override
    public void showNote()
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
    
    @Override
    public void remind()
    {
        System.out.print(" ->[Reminder set in " + getHoursToDeadline() + " hours]\n");
    }
}
