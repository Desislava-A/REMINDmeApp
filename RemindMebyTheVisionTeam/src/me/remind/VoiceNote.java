package me.remind;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Date;

public class VoiceNote extends Note implements Remindable
{
    private String fileName;
    private AudioInputStream inputStream;
    private Clip audioClip;
    
    public VoiceNote(String title, Date deadline, Priority priority, String fileName)
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
    public String getTitleWithType()
    {
        return "[VoiceNote]" + "\n\t" + getTitle();
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
        showNote();
        System.out.print("->[Reminder set in " + getDaysToDeadline() + "]");
    }
}
